package org.example.workingage;

import org.example.calculator.TaxCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class WorkingAgeCalculator implements TaxCalculator {

    private static final double PERCENTAGE_TAX_RATE = 24.00;


    @Override
    public BigDecimal calculateTax(double income) {
        BigDecimal grossIncome = BigDecimal.valueOf(income);
        BigDecimal taxRate = BigDecimal.valueOf(PERCENTAGE_TAX_RATE / 100);
        return grossIncome.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal incomeAfterTax(double income) {
        BigDecimal grossIncome = BigDecimal.valueOf(income);
        BigDecimal rate = BigDecimal.valueOf((100 - PERCENTAGE_TAX_RATE) / 100);
        return grossIncome.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }
}
