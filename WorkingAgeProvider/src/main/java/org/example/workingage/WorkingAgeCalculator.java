package org.example.workingage;

import org.example.calculator.TaxCalculator;

public class WorkingAgeCalculator implements TaxCalculator {

    private static final int TAX_RATE = 24;

    @Override
    public int calculateTax(int income) {
        return income * TAX_RATE / 100;
    }

    @Override
    public int incomeAfterTax(int income) {
        return income * (100 - TAX_RATE) / 100;
    }

}
