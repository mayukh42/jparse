package org.mayukh.jparse.types;

import java.util.LinkedHashMap;
import java.util.Map;

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

    /**
     * TODO: Use ':' instead of '=' (proof of concept)
     */
    @Override
    public String toString() {
        String contentStr = content != null ? content.toString() : "{--null--}";
        return contentStr.replaceAll("=", ":");
    }
}
