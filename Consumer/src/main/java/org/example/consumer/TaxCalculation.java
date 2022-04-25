package org.example.consumer;

import org.example.taxcalculator.TaxCalculator;
import org.example.taxcalculator.TaxCategory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.Scanner;
import java.util.ServiceLoader;

public class TaxCalculation {

    public static Scanner scanner = new Scanner(System.in);
    private final int age;
    private final double income;

    public TaxCalculation(int age, double income) {
        this.age = age;
        this.income = income;
    }

    public void start() {
        NetIncomeAndTax netIncomeAndTax = calculate(income);
        displayResult(income, netIncomeAndTax);
    }

    private void displayResult(double totalIncome, NetIncomeAndTax netIncomeAndTax) {
        BigDecimal income = BigDecimal.valueOf(totalIncome).setScale(2, RoundingMode.HALF_UP);

        System.out.println();
        System.out.println("Result for a " + age + " year old person with a total income of " + income + " kr:");
        System.out.println("NetIncome - " + netIncomeAndTax.netIncome() + " kr");
        System.out.println("Tax - " + netIncomeAndTax.tax() + " kr");
        System.out.println("---------------------------------------------------------------------------");

    }

    private String getCategory() {
        return age > 65 ? "pension" : "ordinary";
    }

    private NetIncomeAndTax calculate(double income) {
        String category = getCategory();
        TaxCalculator calculator = getTaxCalculator(category)
                .orElseThrow(() -> new IllegalArgumentException(category + " category not found"));

        BigDecimal tax = calculator.calculateTax(income);
        BigDecimal netIncome = calculator.incomeAfterTax(income);
        return new NetIncomeAndTax(netIncome, tax);
    }

    private static Optional<TaxCalculator> getTaxCalculator(String category) {
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
