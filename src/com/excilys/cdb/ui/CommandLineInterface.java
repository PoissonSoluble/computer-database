package com.excilys.cdb.ui;

import java.util.Scanner;

public class CommandLineInterface {
	
	public String getMenu() {
		
		StringBuilder sb = new StringBuilder("===== COMPUTER DATABASE =====\n");
		sb.append("--------- MAIN MENU ---------\n");
		sb.append("1/ List computers\n");
		sb.append("2/ List companies\n");
		sb.append("3/ Detail computer\n");
		sb.append("4/ Create computer\n");
		sb.append("5/ Update computer\n");
		sb.append("6/ Remove computer\n\n");

		return sb.toString();
	}
	
	public String getUserInput() {
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		sc.close();
		return input;
	}
	
	public void handleUserChoice(String choice) {
		
	}
	
	public static void main(String[] args) {
		CommandLineInterface cli = new CommandLineInterface();
		System.out.println(cli.getMenu());
	}
}
