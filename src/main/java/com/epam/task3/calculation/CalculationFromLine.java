package com.epam.task3.calculation;

import java.util.*;

/**
 * @author Alexander Pishchala
 *         "sqrt(sqrt(16))+pow10(3)";   => 1002.0
 *         "3+(5+5-3)";     => 10.0
 *         "cube(3)+sqrt(4)+3+(-1+3)+0+1*3+6/2";   => 40.0
 *         "cube(3)+sqrt(4)+3+(1+3)+0+1*3+6/2";   => 42.0
 */
class CalculationFromLine implements Calculator{

    @Override
    public Double calculation(String expression) throws CalculationException {
        List<String> postfix = parsingExpression(expression.replace(" ", ""));
        Deque<Double> stack = new ArrayDeque<>();
        for (String lineValue : postfix) {
            if (Validator.isNumber(lineValue)) {
                if (lineValue.matches(MathExpression.SQRT_FUNCTION_REGEX)) {
                    stack.push(Math.sqrt(stack.pop()));
                } else if (lineValue.matches(MathExpression.CUBE_FUNCTION_REGEX)) {
                    Double tmp = stack.pop();
                    stack.push(tmp * tmp * tmp);
                } else if (lineValue.matches(MathExpression.POW10_FUNCTION_REGEX)) {
                    stack.push(Math.pow(10, stack.pop()));
                } else if (lineValue.matches(MathExpression.PLUS_SIGN_REGEX)) {
                    stack.push(stack.pop() + stack.pop());
                } else if (lineValue.matches(MathExpression.MINUS_SIGN_REGEX)) {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                } else if (lineValue.matches(MathExpression.MULTIPLICATION_SIGN_REGEX)) {
                    stack.push(stack.pop() * stack.pop());
                } else if (lineValue.matches(MathExpression.DIVISION_SIGN_REGEX)) {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                } else if (lineValue.matches(MathExpression.OPERATOR_MINUS_SIGN_REGEX)) {
                    stack.push(-stack.pop());
                } else {
                    stack.push(Double.valueOf(lineValue));
                }
            } else {
                throw new CalculationException("Incorrect element - '" + lineValue + "'");
            }
        }
        return stack.pop();
    }

    @Override
    public List<String> parsingExpression(String infix) throws CalculationException {
        MathExpression expression = new MathExpression();
        List<String> postfix = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringTokenizer tokenizer = new StringTokenizer(infix, MathExpression.DELIMITERS_REGEX, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && expression.isOperator(curr)) {
                return postfix;
            }
            if (curr.matches(MathExpression.SPACE_SIGN_REGEX)) {
                continue;
            }
            if (expression.isFunction(curr)) {
                stack.push(curr);
            } else if (expression.isDelimiter(curr)) {
                if (curr.matches(MathExpression.BRACKET_OPEN_SIGN_REGEX)) {
                    stack.push(curr);
                } else if (curr.matches(MathExpression.BRACKET_CLOSE_SIGN_REGEX)) {
                    while (!stack.peek().matches(MathExpression.BRACKET_OPEN_SIGN_REGEX)) {
                        postfix.add(stack.pop());
                        if (stack.isEmpty()) {
                            throw new CalculationException("The parentheses are not matched. There are not all brackets");
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && expression.isFunction(stack.peek())) {
                        postfix.add(stack.pop());
                    }
                } else {
                    if (curr.matches(MathExpression.MINUS_SIGN_REGEX) && (prev.matches(MathExpression.EMPTY_SIGN_REGEX)
                            || (expression.isDelimiter(prev) && !prev.matches(MathExpression.BRACKET_CLOSE_SIGN_REGEX)))) {
                        curr = MathExpression.OPERATOR_MINUS_SIGN_REGEX;
                    } else {
                        while (!stack.isEmpty() && (expression.priority(curr) <= expression.priority(stack.peek()))) {
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
            if (expression.isOperator(stack.peek())) {
                postfix.add(stack.pop());
            } else {
                return postfix;
            }
        }
        return postfix;
    }
}