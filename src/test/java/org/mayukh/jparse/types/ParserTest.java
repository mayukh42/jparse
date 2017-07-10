package org.mayukh.jparse.types;

import org.junit.Test;
import org.mayukh.jparse.parser.Parser;

/**
 * Created by mayukh42 on 6/22/2017.
 *
 * Tests for jparse - a JSON Parser
 */
public class ParserTest {

    private final Parser parser = new Parser();

    @Test
    public void parseSimple() {
        String data = "{  \n" +
                "   \"name\":\"alice\",\n" +
                "   \"age\":33,\n" +
                "   \"city\":\"San Francisco\"\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void parseListOfStrings() {
        String data = "{\n" +
                "   \"person\": {\n" +
                "      \"name\": \"alice\", \n" +
                "      \"hobbies\": [\"Travel\", \"Photography\"]\n" +
                "   }\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void parseListMiddle() {
        String data = "{\n" +
                "   \"person\": {\n" +
                "      \"name\": \"alice\", \n" +
                "      \"courses\": [\"Parallel Programming\", \"Advanced Algorithms\", \"Reactive Programming\"],\n" +
                "      \"age\": 33, \n" +
                "      \"city\": \"San Francisco\"\n" +
                "   }\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void parseNestedJson() {
        String data = "{\n" +
                "   \"person\":{  \n" +
                "      \"name\":\"alice\",\n" +
                "      \"age\":33,\n" +
                "      \"city\":\"San Francisco\"\n" +
                "   }\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void parseListOfJsons() {
        String data = "{\n" +
                "   \"bags in kg\":[  \n" +
                "      {  \n" +
                "         \"handbag\":12.34\n" +
                "      },\n" +
                "      {  \n" +
                "         \"luggage\":29.75\n" +
                "      }\n" +
                "   ]\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void parseComplexJson() {
        String data = "{  \n" +
                "   \"date\":{  \n" +
                "      \"month\":7,\n" +
                "      \"day\":22,\n" +
                "      \"year\":2017\n" +
                "   },\n" +
                "   \"bags in kg\":[  \n" +
                "      {  \n" +
                "         \"handbag\":12.34\n" +
                "      },\n" +
                "      {  \n" +
                "         \"luggage\":29.75\n" +
                "      }\n" +
                "   ],\n" +
                "   \"trip\":{  \n" +
                "      \"origin\":\"SFO\",\n" +
                "      \"flight\":{  \n" +
                "         \"make\":\"boeing\",\n" +
                "         \"gates\":[  \n" +
                "            \"3A\",\n" +
                "            \"3C\"\n" +
                "         ],\n" +
                "         \"model\":\"747\",\n" +
                "         \"carrier\":\"virgin\"\n" +
                "      },\n" +
                "      \"destination\":\"JFK\"\n" +
                "   },\n" +
                "   \"seats\":[  \n" +
                "      \"21A\",\n" +
                "      \"21B\"\n" +
                "   ],\n" +
                "   \"meal\":\"non veg\",\n" +
                "   \"person\":{  \n" +
                "      \"name\":\"alice\",\n" +
                "      \"age\":\"33\",\n" +
                "      \"city\":\"San Francisco\"\n" +
                "   }\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void testNumbers() {
        String data = "{\n" +
                "   \"date\":/* \n" +
                "      this is a multiline comment, with nested line comment.\n" +
                "      // this is a line comment.*/{  \n" +
                "      \"month\":7,  // this is a line comment\n" +
                "      /*\"day\":22, this is a block comment*/\n" +
                "      \"year\":2017\n" +
                "   }\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }

    @Test
    public void testComments() {
        String data = "{\n" +
                "   \"date\":/* \n" +
                "      this is a multiline comment, with nested line comment.\n" +
                "      // this is a line comment.*/{  \n" +
                "      \"month\":7,  // this is a line comment\n" +
                "      /*\"day\":22, this is a block comment*/\n" +
                "      \"year\":2017\n" +
                "   }\n" +
                "}";
        Value json = parser.parse(data);
        System.out.println(json);
    }
}
