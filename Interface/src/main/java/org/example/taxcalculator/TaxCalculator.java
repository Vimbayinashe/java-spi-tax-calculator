package org.example.taxcalculator;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculateTax(double income);

    BigDecimal incomeAfterTax(double income);
}
