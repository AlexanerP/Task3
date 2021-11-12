package com.epam.task3.calculation;

import java.util.List;

public interface Calculator {

    Double calculation(String expression) throws CalculationException;

    List<String> parsingExpression(String infix) throws CalculationException;
}
