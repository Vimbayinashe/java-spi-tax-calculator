package org.example.calculatorprovider;

import org.example.mapper.Calculate;
import org.example.taxcalculator.TaxCalculator;
import org.example.taxcalculator.TaxCategory;

import java.math.BigDecimal;

@TaxCategory("tax free")
public class TaxFreeCalculator implements TaxCalculator {

    private static final double PERCENTAGE_TAX_RATE = 2.50;

    @Override
    public BigDecimal calculateTax(BigDecimal income) {
        return Calculate.tax(income, PERCENTAGE_TAX_RATE);
    }

    @Override
    public BigDecimal incomeAfterTax(BigDecimal income) {
        return Calculate.netIncome(income, PERCENTAGE_TAX_RATE);
    }
}
