package org.example.calculatorprovider;

import org.example.taxcalculator.TaxCalculator;
import org.example.mapper.Calculate;
import org.example.taxcalculator.TaxCategory;

import java.math.BigDecimal;

@TaxCategory("pension")
public class PensionerCalculator implements TaxCalculator {

    private static final double PERCENTAGE_TAX_RATE = 10.00;

    @Override
    public BigDecimal calculateTax(double income) {
        return Calculate.tax(income, PERCENTAGE_TAX_RATE);
    }

    @Override
    public BigDecimal incomeAfterTax(double income) {
        return Calculate.netIncome(income, PERCENTAGE_TAX_RATE);
    }
}
