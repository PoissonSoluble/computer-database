package com.excilys.cdb.ui;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

public class CommandLineInterface {

    private static Scanner sc;

    public static String getUserInput() {
        return sc.nextLine();
    }

    public static void main(String[] args) {
        new CommandLineInterface().start();
    }

    public String getMenu() {
        StringBuilder sb = new StringBuilder("===== COMPUTER DATABASE =====\n");
        sb.append("--------- MAIN MENU ---------\n");
        Stream.of(UserChoice.values()).forEach(value -> sb.append(value.getTitle()).append("\n"));
        sb.append("\nChoose your action: ");
        return sb.toString();
    }

    public boolean handleChoice(String userInput) {
        try {
            UserChoice choice = Stream.of(UserChoice.values()).filter(v -> v.accept(userInput)).findFirst().get();
            return choice.handleChoice();
        } catch (NoSuchElementException e) {
            System.err.println("This choice is not valid.\n");
            return true;
        }
    }

    public void start() {
        CommandLineInterface.sc = new Scanner(System.in);
        while (true) {
            System.out.print(getMenu());
            if (!handleChoice(getUserInput())) {
                break;
            }
        }
        System.out.println("Goodbye !");
        CommandLineInterface.sc.close();
    }
}
