package org.mayukh.jparse.parser;

import org.mayukh.jparse.types.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 * Created by mayukh42 on 6/21/2017.
 *
 * JSON Parser using a state machine
 */
public class Parser {

    private boolean isNumberChar(char c) {
        return  (c >= '0' && c <= '9') || c == '.' || c == '-';
    }

    /** preprocess()
     *  remove block comments and inline comments
     */
    public String preprocess(String raw) {
        if (raw == null) return null;

        StringBuilder sb = new StringBuilder();
        State blockComment = null, lineComment = null;
        int length = raw.length();
        for (int i = 0; i < length; ) {
            char c = raw.charAt(i);
            if (c == '/' && i < length-1 && raw.charAt(i+1) == '*') {
                // block comment start. enter state only if not in a line comment
                if (lineComment == null) blockComment = State.BLOCKCOMMENT;
                i += 2;
            }
            else if (blockComment != null && i < length-1 && c == '*' && raw.charAt(i+1) == '/') {
                // block comment end
                blockComment = null;
                i += 2;
            }
            else if (c == '/' && i < length-1 && raw.charAt(i+1) == '/') {
                // line comment start. enter state only if not in a block comment
                if (blockComment == null) lineComment = State.LINECOMMENT;
                i += 2;
            }
            else if (lineComment != null && c == '\n') {
                lineComment = null;
                i++;
            }
            else if (blockComment == null && lineComment == null) {
                sb.append(c);
                i++;
            }
            else i++;
        }
        return sb.toString();
    }

    /**
     * JSON Parser using a state machine, in a single pass over the data
     *
     * Stacks:
     *  state: Mode in which the string is currently
     *  node: JSON parent node to which elements need to be added
     *  keys: String to which the current value will be added
     *
     * Others:
     *  from: Index from which substring needs to be built. Apparently, it might appear to require a stack, but a
     *      stack is not required for this, since we cannot be in a string token without ending the earlier one.
     *
     */
    public Value parse(String raw) {
        if (raw == null) return null;

        Deque<State> state = new ArrayDeque<>();
        int from = -1;
        Deque<Value> node = new ArrayDeque<>();
        Deque<String> keys = new ArrayDeque<>();
        String data = preprocess(raw);

        try {
            for (int i = 0; i < data.length(); ) {
                char c = data.charAt(i);
                if (c == '{' && state.isEmpty()) {
                    // start json (root or nested or list element)
                    state.push(State.JSON);
                    node.push(new Json());
                    i++;
                } else if (Objects.equals(state.peek(), State.JSON) && c == '"') {
                    // key start (which also starts a string)
                    state.push(State.KV);   // in KV pair
                    state.push(State.STRING);  // key start
                    i++;
                    from = i;
                } else if (Objects.equals(state.peek(), State.STRING) && c == '"') {
                    // string end
                    state.pop();
                    StringV value = new StringV(data.substring(from, i));
                    from = -1;
                    node.push(value);
                    i++;
                } else if (Objects.equals(state.peek(), State.KV) && c == ':') {
                    // key end
                    String key = ((StringV) node.pop()).getValue();
                    keys.push(key.trim());
                    i++;
                } else if (Objects.equals(state.peek(), State.KV) && c == ',') {
                    // a kv with value as a string or list or json has ended
                    Value value = node.pop();
                    String key = keys.pop();
                    Json parent = (Json) node.peek();
                    parent.add(key, value);
                    i++;
                } else if ((Objects.equals(state.peek(), State.KV) || Objects.equals(state.peek(), State.LIST)) &&
                        c == '"') {
                    // stringv start
                    state.push(State.STRING);
                    i++;
                    from = i;
                } else if ((Objects.equals(state.peek(), State.KV) || Objects.equals(state.peek(), State.LIST)) &&
                        isNumberChar(c)) {
                    // numberV start
                    from = i;
                    state.push(State.NUMBER);
                    i++;
                } else if (Objects.equals(state.peek(), State.NUMBER) && !isNumberChar(c)) {
                    // numberV end
                    state.pop();
                    String numberStr = data.substring(from, i);
                    Double valueD = Double.valueOf(numberStr);
                    NumberV value;
                    // Lazy hack between decimal and non-decimal formats
                    if (valueD.longValue() * 1.0 == valueD) value = new NumberV(Long.valueOf(numberStr));
                    else value = new NumberV(Double.valueOf(numberStr));
                    node.push(value);
                    from = -1;
                    // don't i++ here
                } else if (Objects.equals(state.peek(), State.KV) && c == '[') {
                    // list start
                    state.push(State.LIST);
                    ListV list = new ListV(null);
                    node.push(list);
                    i++;
                } else if (Objects.equals(state.peek(), State.KV) && c == '{') {
                    // value json start
                    state.push(State.JSON);
                    Json jsonV = new Json();
                    node.push(jsonV);
                    i++;
                } else if (Objects.equals(state.peek(), State.LIST) && c == '{') {
                    // json element of list start
                    state.push(State.JSON);
                    Json jsonV = new Json();
                    node.push(jsonV);
                    i++;
                } else if (Objects.equals(state.peek(), State.LIST) && c == ',') {
                    // list element ends
                    Value element = node.pop(); // pop the list element
                    ListV list = (ListV) node.peek();
                    list.add(element);
                    i++;
                } else if (Objects.equals(state.peek(), State.LIST) && c == ']') {
                    // json list ends
                    state.pop();
                    state.pop();    // list always ends a KV, so pop KV

                    Value element = node.pop();         // pop the list element
                    ListV list = (ListV) node.pop();    // pop the list
                    list.add(element);
                    String key = keys.pop();
                    Json parent = (Json) node.peek();
                    parent.add(key, list);
                    i++;
                } else if (Objects.equals(state.peek(), State.KV) && c == '}') {
                    // last kv of current json
                    state.pop();    // pop KV
                    state.pop();    // pop JSON
                    String key = keys.pop();
                    Value value = node.pop();
                    Json parent = (Json) node.peek();
                    parent.add(key, value);
                    i++;
                } else if (Objects.equals(state.peek(), State.JSON) && c == '}') {
                    // end json
                    state.pop();
                    i++;
                } else i++;
            }
        }
        catch (Exception e) {
            throw new JparseException("Invalid JSON document. " + e.getMessage());
        }

        if (!state.isEmpty() || node.size() != 1 || from != -1) {
            String debugInfo = "State stack: " + state + ", From index: " + from + ", Node stack: " + node +
                    ", Key stack: " + keys;
            throw new JparseException(debugInfo);
        }

        // at the end, the DOM is the only element left in node stack
        return node.pop();
    }

    /**
     * Read a JSON file and parse the content.
     * By default, the file is assumed to be in src/main/resources/
     */
    public Value parseJsonFile(String location, String file) {
        if (location == null || location.length() == 0) location = "src/main/resources/json/";
        Path path = Paths.get(location, file);
        byte[] bytes = null;
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parse(new String(bytes));
    }
}
