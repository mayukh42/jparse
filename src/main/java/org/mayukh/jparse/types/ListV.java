package org.mayukh.jparse.types;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayukh42 on 6/21/2017.
 */
public class ListV extends Value {

    private List<Value> values;

    public ListV(List<Value> values) {
        this.values = values;
    }

    public Value get(int index) {
        return values.get(index);
    }

    public void add(Value value) {
        if (values == null) values = new ArrayList<>();
        values.add(value);
    }

    public List<Value> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return values != null ? values.toString() : "[--null--]";
    }
}
