package org.example.consumer;

import java.math.BigDecimal;

public record TaxResult(BigDecimal totalIncome, BigDecimal netIncome, BigDecimal tax) {
}
