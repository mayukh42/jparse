package org.mayukh.jparse.types;

/**
 * Created by mayukh42 on 6/21/2017.
 */
public class StringV extends Value {

    private final String value;

    public StringV(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return '"' + value + '"';
    }
}
