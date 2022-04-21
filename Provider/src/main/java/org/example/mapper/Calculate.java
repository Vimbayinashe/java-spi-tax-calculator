package org.example.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculate {

    public static BigDecimal tax(double income, double percentageTaxRate) {
        BigDecimal grossIncome = BigDecimal.valueOf(income);
        BigDecimal taxRate = BigDecimal.valueOf(percentageTaxRate / 100);
        return grossIncome.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal netIncome(double income, double percentageTaxRate) {
        BigDecimal grossIncome = BigDecimal.valueOf(income);
        BigDecimal rate = BigDecimal.valueOf((100 - percentageTaxRate) / 100);
        return grossIncome.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

}
