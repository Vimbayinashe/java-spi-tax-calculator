package org.example.consumer;

import org.example.taxcalculator.TaxCalculator;

import java.util.ServiceLoader;

public class Main {

    public static void main(String[] args) {
        TaxCalculator calculator = getTaxCalculator("Working");
        var tax = calculator.calculateTax(10000);
        var netIncome = calculator.incomeAfterTax(10000);
        System.out.println("Tax is " + tax + " kr + net income is " + netIncome + "kr.");
    }

    private static TaxCalculator getTaxCalculator(String ageGroup) {
        ServiceLoader<TaxCalculator> serviceLoader = ServiceLoader.load(TaxCalculator.class);

        var calculator = serviceLoader.stream()
                .filter(provider -> provider.type().getSimpleName().startsWith(ageGroup))
                .map(ServiceLoader.Provider::get)
                .findFirst();

        return calculator.orElseThrow(() -> new IllegalArgumentException(ageGroup + " age group not found"));
    }

}
