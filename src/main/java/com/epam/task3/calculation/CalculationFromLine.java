package com.epam.task3.calculation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author Alexander Pishchala
 *         "sqrt(sqrt(16))+pow10(3)";   => 1002.0
 *         "3+(5+5-3)";     => 10.0
 *         "cube(3)+sqrt(4)+3+(-1+3)+0+1*3+6/2";   => 40.0
 *         "cube(3)+sqrt(4)+3+(1+3)+0+1*3+6/2";   => 42.0
 */
class CalculationFromLine extends MathExpression implements Calculator{

    @Override
    public Double calculation(String expression) throws CalculationException {
        List<String> postfix = parsingLine(expression.replace(" ", ""));
        Deque<Double> stack = new ArrayDeque<>();
        for (String lineValue : postfix) {
            if (Validator.isNumber(lineValue)) {
                if (lineValue.matches(SQRT_FUNCTION_REGEX)) {
                    stack.push(Math.sqrt(stack.pop()));
                } else if (lineValue.matches(CUBE_FUNCTION_REGEX)) {
                    Double tmp = stack.pop();
                    stack.push(tmp * tmp * tmp);
                } else if (lineValue.matches(POW10_FUNCTION_REGEX)) {
                    stack.push(Math.pow(10, stack.pop()));
                } else if (lineValue.matches(PLUS_SIGN_REGEX)) {
                    stack.push(stack.pop() + stack.pop());
                } else if (lineValue.matches(MINUS_SIGN_REGEX)) {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a - b);
                } else if (lineValue.matches(MULTIPLICATION_SIGN_REGEX)) {
                    stack.push(stack.pop() * stack.pop());
                } else if (lineValue.matches(DIVISION_SIGN_REGEX)) {
                    Double b = stack.pop(), a = stack.pop();
                    stack.push(a / b);
                } else if (lineValue.matches(OPERATOR_MINUS_SIGN_REGEX)) {
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
}