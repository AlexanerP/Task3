package com.epam.task3.calculation;

import java.util.*;
import java.lang.*;

public class MathExpression {

    static final String OPERATORS_REGEX = "+-*/";
    static final String DELIMITERS_REGEX = "() +-*/";
    static final String BRACKET_OPEN_SIGN_REGEX = "\\(";
    static final String BRACKET_CLOSE_SIGN_REGEX = "\\)";
    static final String EMPTY_SIGN_REGEX = "";
    static final String SPACE_SIGN_REGEX = "";
    static final String PLUS_SIGN_REGEX = "\\+";
    static final String MINUS_SIGN_REGEX = "-";
    static final String MULTIPLICATION_SIGN_REGEX = "\\*";
    static final String DIVISION_SIGN_REGEX = "/";
    static final String OPERATOR_MINUS_SIGN_REGEX = "u-";
    static final String SQRT_FUNCTION_REGEX = "sqrt";
    static final String CUBE_FUNCTION_REGEX = "cube";
    static final String POW10_FUNCTION_REGEX = "pow10";

    private boolean flag = true;

    private boolean isDelimiter(String token) {
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

    private boolean isOperator(String token) {
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

    private boolean isFunction(String token) {
        if (token.matches(SQRT_FUNCTION_REGEX) || token.matches(CUBE_FUNCTION_REGEX) || token.matches(POW10_FUNCTION_REGEX)) {
            return true;
        }
        return false;
    }

    private int priority(String token) {
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

    public List<String> parsingLine(String infix) throws CalculationException {
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, DELIMITERS_REGEX, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                return postfix;
            }
            if (curr.matches(SPACE_SIGN_REGEX)) {
                continue;
            }
            if (isFunction(curr)) {
                stack.push(curr);
            } else if (isDelimiter(curr)) {
                if (curr.matches(BRACKET_OPEN_SIGN_REGEX)) {
                    stack.push(curr);
                } else if (curr.matches(BRACKET_CLOSE_SIGN_REGEX)) {
                    while (!stack.peek().matches(BRACKET_OPEN_SIGN_REGEX)) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            throw new CalculationException("The parentheses are not matched. There are not all brackets");
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } else {
                    if (curr.matches(MINUS_SIGN_REGEX) && (prev.matches(EMPTY_SIGN_REGEX) || (isDelimiter(prev)
                            && !prev.matches(BRACKET_CLOSE_SIGN_REGEX)))) {
                        curr = OPERATOR_MINUS_SIGN_REGEX;
                    } else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }
                    }
                    stack.push(curr);
                }
            } else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) {
                postfix.add(stack.pop());
            } else {
                return postfix;
            }
        }
        return postfix;
    }
}