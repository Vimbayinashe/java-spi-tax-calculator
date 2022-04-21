package org.example.provide;

import org.example.taxcalculator.TaxCalculator;
import org.example.mapper.Calculate;

import java.math.BigDecimal;

public class WorkingAgeCalculator implements TaxCalculator {

    private static final double PERCENTAGE_TAX_RATE = 24.00;


    @Override
    public BigDecimal calculateTax(double income) {
        return Calculate.tax(income, PERCENTAGE_TAX_RATE);
    }

    @Override
    public BigDecimal incomeAfterTax(double income) {
        return Calculate.netIncome(income, PERCENTAGE_TAX_RATE);
    }
}
