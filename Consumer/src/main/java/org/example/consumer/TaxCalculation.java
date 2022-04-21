package org.example.consumer;

import org.example.taxcalculator.TaxCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;
import java.util.ServiceLoader;

public class TaxCalculation {

    public static Scanner scanner = new Scanner(System.in);
    private final int age;

    public TaxCalculation(int age) {
        this.age = age;
    }

    public void start() {
        double income = getTotalIncome();
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

    private NetIncomeAndTax calculate(double income) {
        return age > 65 ? pensioner(income) : workingAge(income);
    }

    private double getTotalIncome() {
        double income;
        while(true) {
            System.out.println("Please enter your total income");
            String input = scanner.nextLine().replace(",", ".");

            try {
                income = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal or whole number.");
                continue;
            }

            if (income > 0)
                break;
            else
                System.out.println("Please enter a number greater than 0.");
        }
        return income;
    }

    private NetIncomeAndTax pensioner(double income) {
        TaxCalculator calculator = getTaxCalculator("Pensioner");
        var tax = calculator.calculateTax(income);
        var netIncome = calculator.incomeAfterTax(income);
        return new NetIncomeAndTax(netIncome, tax);
        //System.out.println("Pensioner: tax is " + tax + " kr + net income is " + netIncome + "kr.");
    }

    private NetIncomeAndTax workingAge(double income) {
        TaxCalculator calculator = getTaxCalculator("Working");
        var tax = calculator.calculateTax(income);
        var netIncome = calculator.incomeAfterTax(income);
        return new NetIncomeAndTax(netIncome, tax);
        // System.out.println("Age 15 - 65: tax is " + tax + " kr + net income is " + netIncome + "kr.");
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
