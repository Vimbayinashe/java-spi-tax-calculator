package org.example.consumer;

import org.example.taxcalculator.TaxCalculator;
import org.example.taxcalculator.TaxCategory;

import java.util.*;

public class Menu {

    private static final Scanner scanner = new Scanner(System.in);
    private final List<String> taxOptions;

    public Menu() {
        taxOptions = getTaxOptions();
    }

    public void run() {
        displayGreeting();
        int choice;
        do {
            printMenu();
            choice = getChoice();
            handleChoice(choice);
        } while (valid(choice));
    }

    private void handleChoice(int choice) {
        if (choice == 0)
            exit();
        else if (choice < 0 || choice > taxOptions.size())
            invalidChoice("category from the menu");
        else
            taxCalculation(choice);
    }

    private void taxCalculation(int choice) {
        TaxResult taxResult  = calculateTax(choice);
        displayResult(taxResult, taxCategory(choice));
    }

    private TaxResult calculateTax(int choice) {
        String taxCategory = taxCategory(choice);
        double income = getTotalIncome();
        return getTaxResult(taxCategory, income);
    }

    private String taxCategory(int choice) {
        return taxOptions.get(choice - 1);
    }

    private TaxResult getTaxResult(String taxCategory, double income) {
        TaxCalculation taxCalculation = new TaxCalculation(taxCategory, income);
        return taxCalculation.calculate();
    }

    private double getTotalIncome() {
        double income;
        while (true) {
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

    private boolean valid(int choice) {
        return choice != 0;
    }

    private void invalidChoice(String property) {
        System.out.println("Please enter a valid " + property);
    }

    private void exit() {
        System.out.println("Thank-you for visiting Tax Declaration Office Help Service!");
    }

    private int getChoice() {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                invalidChoice("number");
            }
        }
        return choice;
    }

    private void displayResult(TaxResult taxResult, String taxCategory) {
        System.out.println();
        System.out.println("Result for an individual in the '" + taxCategory +  "' tax bracket:");
        System.out.println("Total income - " + taxResult.totalIncome() + " kr");
        System.out.println("NetIncome - " + taxResult.netIncome() + " kr");
        System.out.println("Tax - " + taxResult.tax() + " kr");
        System.out.println("---------------------------------------------------------------------------");
    }

    private void printMenu() {
        int count = 1;
        System.out.println("Please choose a tax category below or '0' to exit the program.");
        for (String taxOption : taxOptions) {
            System.out.println(count + ". " + taxOption.substring(0, 1).toUpperCase() + taxOption.substring(1));
            count++;
        }
    }

    private void displayGreeting() {
        System.out.println(
                """
                        =============================================================================
                        Welcome to the Tax Declaration Office Help Service
                        """
        );
    }

    private List<String> getTaxOptions() {
        ServiceLoader<TaxCalculator> serviceLoader = ServiceLoader.load(TaxCalculator.class);
        List<String> list = new ArrayList<>();

        for (TaxCalculator calculator : serviceLoader) {
            TaxCategory annotation = calculator.getClass().getAnnotation(TaxCategory.class);
            list.add(annotation.value());
        }
        return list;
    }
}
