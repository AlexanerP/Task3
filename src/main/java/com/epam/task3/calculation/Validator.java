package com.epam.task3.calculation;

public class Validator {

    private static final String REGEX_MATH = "cube|sqrt|pow10|u-|^[0-9]*[.,]?[0-9]+$|[+-/*()]";

    private Validator() {}

    public static boolean isNumber(String text) {
        if(text.trim().matches(REGEX_MATH)) {
            return true;
        }
        return false;
    }
}
