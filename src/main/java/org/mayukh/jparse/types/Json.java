package org.mayukh.jparse.types;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mayukh42 on 6/21/2017.
 *
 * JSON type. Also can be a Value
 * The LinkedHashMap maintains order of insertion.
 */
public class Json extends Value {

    private Map<String, Value> content;

    public Json() {
        this.content = new LinkedHashMap<>();
    }

    public Value get(String key) {
        return content.get(key);
    }

    public void add(String key, Value value) {
        if (content == null) content = new LinkedHashMap<>();
        if (key != null) content.put(key, value);
    }

    public Set<String> keys() {
        return content.keySet();
    }

    /**
     * toString(): manually create key:value pairs
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (content == null) {
            sb.append("{--null--}");
            return sb.toString();
        }

        sb.append('{');
        for (String key : content.keySet()) {
            sb.append('"').append(key).append('"').append(':');
            sb.append(content.get(key)).append(',');
        }
        sb.replace(sb.length()-1, sb.length(), "}");
        return sb.toString();
    }
}
