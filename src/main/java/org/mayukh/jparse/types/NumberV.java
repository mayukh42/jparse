package org.mayukh.jparse.types;

/**
 * Created by mayukh42 on 6/25/2017.
 * The Number type in JSON: Stores numbers (long and double)
 */
public class NumberV extends Value {

    private Number value;

    public NumberV(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "--null--";
    }
}
