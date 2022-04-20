package org.example.calculator;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculateTax(double income);

    BigDecimal incomeAfterTax(double income);
}
