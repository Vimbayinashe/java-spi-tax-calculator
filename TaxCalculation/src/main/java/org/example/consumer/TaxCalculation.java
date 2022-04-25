package org.example.consumer;

import org.example.taxcalculator.TaxCalculator;
import org.example.taxcalculator.TaxCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.ServiceLoader;

public class TaxCalculation {

    private final String category;
    private final BigDecimal income;

    public TaxCalculation( String category, double income) {
        this.category = category;
        this.income = BigDecimal.valueOf(income).setScale(2, RoundingMode.HALF_UP);
    }

    public TaxResult calculate() {
        return taxResult();
    }

    private TaxResult taxResult() {
        TaxCalculator calculator = getTaxCalculator(category)
                .orElseThrow(() -> new IllegalArgumentException(category + " category not found"));
        BigDecimal tax = calculator.calculateTax(income);
        BigDecimal netIncome = calculator.incomeAfterTax(income);
        return new TaxResult(income, netIncome, tax);
    }

    private Optional<TaxCalculator> getTaxCalculator(String category) {
        ServiceLoader<TaxCalculator> serviceLoader = ServiceLoader.load(TaxCalculator.class);
        Optional<TaxCalculator> taxCalculator = Optional.empty();

        for (TaxCalculator calculator : serviceLoader) {
            TaxCategory annotation = calculator.getClass().getAnnotation(TaxCategory.class);
            if (annotation != null && category.equals(annotation.value()))
                taxCalculator = Optional.of(calculator);
        }
        return taxCalculator;
    }
}
