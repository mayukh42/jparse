package org.mayukh.jparse.types;

/**
 * Created by mayukh42 on 6/21/2017.
 *
 * Parent type of all JSON values (The Composite Design Pattern)
 * Simplified JSON grammar:
 *  JSON	::=	Map<String, Value>
 *  Value 	::= JSON | List | String
 *  Array 	::= JSON :: JSONS | String :: Strings
 *
 *  TODO: Implement true, false, and null literals
 */
public abstract class Value {
}
