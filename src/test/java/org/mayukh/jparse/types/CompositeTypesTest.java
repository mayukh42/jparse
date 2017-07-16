package org.mayukh.jparse.types;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by mayukh42 on 6/21/2017.
 *
 * Composite types in JSON
 */
public class CompositeTypesTest {

    /**
     * {
     *     name: amaron,
     *     supports: [punto, polo, baleno],
     *     details: {
     *         type: flo 50,
     *         mfg: 2017
     *     }
     * }
     *
     * The Composite design pattern directly facilitates Interpreter design pattern when invoking toString() methods
     */
    @Test
    public void testBasicJson() {
        Json json = new Json() {
                {
                    add("name", new StringV("amaron"));
                    add("supports", new ListV(Arrays.asList(
                            new StringV("punto"),
                            new StringV("polo"),
                            new StringV("baleno")
                    )));
                    add("details", new Json() {
                        {
                            add("type", new StringV("flo 50"));
                            add("mfg", new StringV("2017"));
                        }
                    });
                }
            };
        System.out.println(json);
    }

    @Test
    public void testMapFunction() {

    }
}
