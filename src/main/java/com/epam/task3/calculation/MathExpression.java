package com.epam.task3.calculation;

import java.lang.*;

public class MathExpression {

    public static final String OPERATORS_REGEX = "+-*/";
    public static final String DELIMITERS_REGEX = "() +-*/";
    public static final String BRACKET_OPEN_SIGN_REGEX = "\\(";
    public static final String BRACKET_CLOSE_SIGN_REGEX = "\\)";
    public static final String EMPTY_SIGN_REGEX = "";
    public static final String SPACE_SIGN_REGEX = "";
    public static final String PLUS_SIGN_REGEX = "\\+";
    public static final String MINUS_SIGN_REGEX = "-";
    public static final String MULTIPLICATION_SIGN_REGEX = "\\*";
    public static final String DIVISION_SIGN_REGEX = "/";
    public static final String OPERATOR_MINUS_SIGN_REGEX = "u-";
    public static final String SQRT_FUNCTION_REGEX = "sqrt";
    public static final String CUBE_FUNCTION_REGEX = "cube";
    public static final String POW10_FUNCTION_REGEX = "pow10";

    public boolean isDelimiter(String token) {
        if (token.length() != 1) {
            return false;
        }
        for (int i = 0; i < DELIMITERS_REGEX.length(); i++) {
            if (token.charAt(0) == DELIMITERS_REGEX.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOperator(String token) {
        if (token.matches(OPERATOR_MINUS_SIGN_REGEX)) {
            return true;
        }
        for (int i = 0; i < OPERATORS_REGEX.length(); i++) {
            if (token.charAt(0) == OPERATORS_REGEX.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFunction(String token) {
        if (token.matches(SQRT_FUNCTION_REGEX) || token.matches(CUBE_FUNCTION_REGEX) || token.matches(POW10_FUNCTION_REGEX)) {
            return true;
        }
        return false;
    }

    public int priority(String token) {
        if (token.matches(BRACKET_OPEN_SIGN_REGEX)) {
            return 1;
        }
        if (token.matches(PLUS_SIGN_REGEX) || token.matches(MINUS_SIGN_REGEX)) {
            return 2;
        }
        if (token.matches(MULTIPLICATION_SIGN_REGEX) || token.matches(DIVISION_SIGN_REGEX)) {
            return 3;
        }
        return 4;
    }
}