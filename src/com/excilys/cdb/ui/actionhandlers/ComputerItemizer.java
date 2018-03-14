package com.excilys.cdb.ui.actionhandlers;

import java.time.LocalDate;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CommandLineInterface;

public class ComputerItemizer implements CLIActionHandler {

	private ComputerService service = ComputerService.INSTANCE;

	@Override
	public void handle() {
		try {
			Long id = askComputerID();
			Computer computer = service.detailComputer(id);
			printComputer(computer);
			CommandLineInterface.getUserInput();
		} catch (NumberFormatException e) {
			System.err.println("This is not a proper ID format. (an integer)");
		}
	}

	private void printComputer(Computer computer) {
		if (computer == null) {
			System.err.println("This computer does not exist.");
		} else {
			System.out.println(constructDetailString(computer));
		}
	}

	private String constructDetailString(Computer computer) {
		StringBuilder sb = new StringBuilder("\n== Details of computer#").append(computer.getId()).append(" ==\n");
		sb.append("Name: ").append(computer.getName()).append("\n");
		sb.append("Introduced date: ");
		sb.append(getFormatedDate(computer.getIntroduced()));
		sb.append("Discontinued date: ");
		sb.append(getFormatedDate(computer.getDiscontinued()));
		sb.append("Company: ");
		sb.append(getFormatedCompany(computer.getCompany()));
		sb.append("(Press ENTER to continue)");
		return sb.toString();
	}

	private StringBuilder getFormatedDate(LocalDate date) {
		StringBuilder sb = new StringBuilder();
		if(date == null) {
			sb.append("Not specified.").append("\n");
		}else {
			sb.append(date.getMonthValue()).append(" ");
			sb.append(date.getMonth()).append(" ");
			sb.append(date.getYear()).append("\n");
		}
		return sb;
	}

	private StringBuilder getFormatedCompany(Company company) {
		StringBuilder sb = new StringBuilder();
		if(company.getId() == 0) {
			sb.append("Not specified.").append("\n");
		}else {
			sb.append(company.getName()).append(" (#").append(company.getId()).append(")").append("\n");
		}
		return sb;
	}

	private Long askComputerID() throws NumberFormatException {
		System.out.print("Enter the computer ID: ");
		String stringId = CommandLineInterface.getUserInput();
		return Long.parseLong(stringId);
	}
}
