package org.example.consumer;

import org.example.taxcalculator.TaxCalculator;

import java.util.ServiceLoader;

public class Menu {

    public void run() {
        pensioner();
        workingAge();
    }

    private void pensioner() {
        TaxCalculator calculator = getTaxCalculator("Pensioner");
        var tax = calculator.calculateTax(10000);
        var netIncome = calculator.incomeAfterTax(10000);
        System.out.println("Pensioner: tax is " + tax + " kr + net income is " + netIncome + "kr.");
    }

    private void workingAge() {
        TaxCalculator calculator = getTaxCalculator("Working");
        var tax = calculator.calculateTax(10000);
        var netIncome = calculator.incomeAfterTax(10000);
        System.out.println("Age 15 - 65: tax is " + tax + " kr + net income is " + netIncome + "kr.");
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
