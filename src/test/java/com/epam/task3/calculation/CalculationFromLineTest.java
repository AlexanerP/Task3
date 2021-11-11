package com.epam.task3.calculation;

import org.junit.Test;

import static org.junit.Assert.*;

public class CalculationFromLineTest {

    @Test
    public void calculationTrue() throws CalculationException {
        Calculator calculator = new CalculationFromLine();
        String expression = "cube(3)+ sqrt(4)+3  +(-1+3)+0+1*3+6/2";
        double expected = 40.0;
        double actual = calculator.calculation(expression);

        assertTrue(expected == actual);
    }

    @Test
    public void calculationFalse() throws CalculationException {
        Calculator calculator = new CalculationFromLine();
        String expression = "sqrt(4) + 2";
        double expected = 5;
        double actual = calculator.calculation(expression);

        assertFalse(expected == actual);
    }
}