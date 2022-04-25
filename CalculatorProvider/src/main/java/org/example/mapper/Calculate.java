package org.example.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculate {

    public static BigDecimal tax(BigDecimal income, double percentageTaxRate) {
        BigDecimal taxRate = BigDecimal.valueOf(percentageTaxRate / 100);
        return income.multiply(taxRate).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal netIncome(BigDecimal income, double percentageTaxRate) {
        BigDecimal rate = BigDecimal.valueOf((100 - percentageTaxRate) / 100);
        return income.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

}
