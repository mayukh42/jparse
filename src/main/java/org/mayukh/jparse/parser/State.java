package org.mayukh.jparse.parser;

/**
 * Created by mayukh42 on 6/21/2017.
 */
public enum State {

    JSON, KV, LIST, STRING, NUMBER, BLOCKCOMMENT, LINECOMMENT, EMPTYLIST, EMPTYJSON
}
