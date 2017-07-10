package org.mayukh.jparse.types;

import org.junit.Test;
import org.mayukh.jparse.parser.Parser;

/**
 * Created by mayukh42 on 6/25/2017.
 *
 * Tests for jparse: a JSON parser
 *  Various JSON files
 */
public class FileParserTest {

    private final Parser parser = new Parser();
    private final String location = "src/main/resources/json";

    private Value parseFile(String location, String file) {
        return parser.parseJsonFile(location, file);
    }

    @Test
    public void parseComplexFile() {
        Value json = parseFile(location, "complex.json");
        System.out.println(json);
    }

    @Test
    public void parseSimpleFile() {
        Value json = parseFile(location, "simple.json");
        System.out.println(json);
    }
}
