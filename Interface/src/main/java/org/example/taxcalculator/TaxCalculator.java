package org.example.taxcalculator;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculateTax(BigDecimal income);

    BigDecimal incomeAfterTax(BigDecimal income);
}
