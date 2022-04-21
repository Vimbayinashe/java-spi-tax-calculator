package org.example.consumer;

import java.math.BigDecimal;

public record NetIncomeAndTax(BigDecimal netIncome, BigDecimal tax) {
}
