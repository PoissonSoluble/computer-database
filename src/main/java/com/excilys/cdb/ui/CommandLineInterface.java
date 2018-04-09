package com.excilys.cdb.ui;

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
        return Stream.of(UserChoice.values()).filter(v -> v.accept(userInput)).findFirst().orElse(UserChoice.ANY)
                .handleChoice();

    }

    public void start() {
        CommandLineInterface.sc = new Scanner(System.in);
        do {
            System.out.print(getMenu());
        } while (handleChoice(getUserInput()));
        CommandLineInterface.sc.close();
    }
}
