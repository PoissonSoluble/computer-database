package com.excilys.cdb.ui.actionhandlers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.CommandLineInterface;

public enum CLIComputerAPI{
	INSTANCE;
	
	public Long askComputerID() throws NumberFormatException {
		System.out.print("Enter the computer ID: ");
		String stringId = CommandLineInterface.getUserInput();
		return Long.parseLong(stringId);
	}
	
	private Long askCompany() throws NumberFormatException {
		System.out.println("Enter a company ID (ENTER to ignore): ");
		String input = CommandLineInterface.getUserInput();
		if (input.equals("")) {
			return null;
		} else {
			return Long.parseLong(input);
		}
	}

	private LocalDate askDate() throws DateTimeParseException {
		System.out.print("Enter a date (format 'yyyy-mm-dd', ENTER to ignore): ");
		String input = CommandLineInterface.getUserInput();
		if (input.equals("")) {
			return null;
		} else {
			return LocalDate.parse(input);
		}
	}
	
	private String askName() {
		System.out.print("\nEnter a name: ");
		String input = CommandLineInterface.getUserInput();
		if (input.equals("")) {
			return null;
		} else {
			return input;
		}
	}

	private boolean readCompany(Computer computer) {
		try {
			Company company = new Company();
			company.setId(askCompany());
			computer.setCompany(company);
		}catch (NumberFormatException e) {
			System.out.println("Wrong company ID format. (need an integer)");
			return false;
		}
		return true;
	}
	
	private boolean readDates(Computer computer) {
		try {
			readIntroduced(computer);
			readDiscontinued(computer);
		} catch (DateTimeParseException e) {
			System.out.println("Wrong date format.");
			return false;
		}
		return true;
	}

	private void readDiscontinued(Computer computer) {
		if(computer.getIntroduced() != null) {
			System.out.print("Discontinued date. ");
			computer.setDiscontinued(askDate());
		}
	}

	private void readIntroduced(Computer computer) {
		System.out.print("Introduced date. ");
		computer.setIntroduced(askDate());
	}

	private boolean readName(Computer computer) {
		computer.setName(askName());
		if(computer.getName() == null) {
			System.out.println("The name cannot be null.");
			return false;
		}
		return true;
	}

	protected Computer askParametersForComputer() {
		Computer computer = new Computer();
		if(!readName(computer) || !readDates(computer) || !readCompany(computer)) {
			return null;
		}
		return computer;
	}

}
