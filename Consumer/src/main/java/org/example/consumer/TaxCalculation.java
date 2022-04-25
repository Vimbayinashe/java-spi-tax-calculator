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
    private final String category;
    private final double income;

    public TaxCalculation(int age, double income) {
        this.age = age;
        this.category = category(age);
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

    private String category(double income) {
        return age > 65 ? "pension" : "ordinary";
    }

    private NetIncomeAndTax calculate(double income) {
        return age > 65 ? pensioner(income) : workingAge(income);
        // return handleCalculation(income);
    }



    private NetIncomeAndTax pensioner(double income) {
        TaxCalculator calculator = getTaxCalculator("pension");
        var tax = calculator.calculateTax(income);
        var netIncome = calculator.incomeAfterTax(income);
        return new NetIncomeAndTax(netIncome, tax);
    }

    private NetIncomeAndTax workingAge(double income) {
        TaxCalculator calculator = getTaxCalculator("ordinary");
        var tax = calculator.calculateTax(income);
        var netIncome = calculator.incomeAfterTax(income);
        return new NetIncomeAndTax(netIncome, tax);
    }

    private static TaxCalculator getTaxCalculator(String category) {
        ServiceLoader<TaxCalculator> serviceLoader = ServiceLoader.load(TaxCalculator.class);
        Optional<TaxCalculator> taxCalculator = Optional.empty();

        for (TaxCalculator calculator : serviceLoader) {
            var annotation = calculator.getClass().getAnnotation(TaxCategory.class);

            if (annotation != null && category.equals(annotation.value()))
                taxCalculator = Optional.of(calculator);
        }

        return taxCalculator.orElseThrow(() -> new IllegalArgumentException(category + " category not found"));
    }

    private static boolean hasCategory(String category, ServiceLoader.Provider<TaxCalculator> taxCalculator) {
        System.out.print("has category: ");
        System.out.println(category.equals(taxCalculator.getClass().getAnnotation(TaxCategory.class).value()));
        return category.equals(taxCalculator.getClass().getAnnotation(TaxCategory.class).value());
    }

    private static boolean hasAnnotationValue(ServiceLoader.Provider<TaxCalculator> taxCalculator) {
        System.out.print("has annotation value: ");
        System.out.println(taxCalculator.getClass().getAnnotation(TaxCategory.class) != null);
        return taxCalculator.getClass().getAnnotation(TaxCategory.class) != null;
    }


    private NetIncomeAndTax handleCalculation(double income) {
        String category = age > 65 ? "pension" : "ordinary";

        TaxCalculator calculator = getTaxCalculator(category);
        var tax = calculator.calculateTax(income);
        var netIncome = calculator.incomeAfterTax(income);
        return new NetIncomeAndTax(netIncome, tax);
    }


    public static void main(String[] args) {
        ServiceLoader<TaxCalculator> serviceLoader = ServiceLoader.load(TaxCalculator.class);

        for (TaxCalculator calculator : serviceLoader) {
            var annotation = calculator.getClass().getAnnotation(TaxCategory.class);

            if (annotation != null) {
                System.out.println("Annotation on class - " + annotation.value());

                if ("pension".equals(annotation.value()))
                    System.out.println(annotation.value() + " is equal to " + "pension");

            }

            System.out.println("------------------------------");
        }
    }
}
