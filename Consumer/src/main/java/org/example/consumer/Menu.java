package org.example.consumer;

import java.util.Scanner;

public class Menu {

    public static Scanner scanner = new Scanner(System.in);

    public void run() {
        displayGreeting();
        int choice;
        do {
            printMenu();
            choice = getAge();
            handleChoice(choice);
        } while (valid(choice) );
    }

    private boolean valid(int choice) {
        return choice < 0 || choice >= 15;
    }

    private void handleChoice(int age) {
        if(age == 0)
            exit();
        else if(age < 0)
            invalidAge();
        else if(age < 15)
            minor();
        else
            calculateTax(age);
    }

    private void calculateTax(int age) {
        TaxCalculation taxCalculation = new TaxCalculation(age);
        taxCalculation.start();
    }

    private void minor() {
        System.out.println("Please ask a guardian to contact the Tax Office on your behalf.");
    }

    private void invalidAge() {
        System.out.println("Please enter a valid age.");
    }

    private void exit() {
        System.out.println("Thank-you for visiting Tax Declaration Office Help Service!");
    }

    private int getAge() {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                invalidAge();
            }
        }
        return choice;
    }

    private void printMenu() {
        System.out.println("Please enter your age to calculate your net income & tax or '0' to exit ...");
    }

    private void displayGreeting() {
        System.out.println(
                """
                =============================================================================
                Welcome to the Tax Declaration Office Help Service
                """
        );
    }


}
