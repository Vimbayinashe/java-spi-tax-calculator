package org.example.mapper;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CalculateTest {
    private final BigDecimal income = BigDecimal.valueOf(10_505.00);
    private final double percentageTaxRate = 10.00;

    @Test
    void givenGrossIncomeAndTaxRateShouldReturnTax() {
        BigDecimal result = Calculate.netIncome(income, percentageTaxRate);

        assertThat(result).isEqualTo("9454.50");
    }

    @Test
    void givenGrossIncomeAndTaxRateShouldReturnNetIncome() {
        BigDecimal result = Calculate.tax(income, percentageTaxRate);

        assertThat(result).isEqualTo("1050.50");
    }
}