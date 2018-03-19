package com.excilys.cdb.ui.actionhandlers;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CommandLineInterface;

public class ComputerItemizer implements CLIActionHandler {

	private ComputerService service = ComputerService.INSTANCE;
	private CLIComputerAPI cliApi = CLIComputerAPI.INSTANCE;

	@Override
	public void handle() {
		try {
			Long id = cliApi.askComputerID();
			Computer computer = service.detailComputer(id);
			printComputer(computer);
			CommandLineInterface.getUserInput();
		} catch (NumberFormatException e) {
			System.out.println("This is not a proper ID format. (an integer)");
		}
	}

	private void printComputer(Computer computer) {
		if (computer == null) {
			System.out.println("This computer does not exist.");
		} else {
			System.out.println(constructDetailString(computer));
		}
	}

	private String constructDetailString(Computer computer) {
		StringBuilder sb = new StringBuilder("\n== Details of computer#").append(computer.getId().get()).append(" ==\n");
		sb.append("Name: ").append(computer.getName().get()).append("\n");
		sb.append("Introduced date: ");
		sb.append(getFormatedDate(computer.getIntroduced()));
		sb.append("Discontinued date: ");
		sb.append(getFormatedDate(computer.getDiscontinued()));
		sb.append("Company: ");
		sb.append(getFormatedCompany(computer.getCompany()));
		sb.append("(Press ENTER to continue)\n");
		return sb.toString();
	}

	private StringBuilder getFormatedDate(Optional<LocalDate> date) {
		StringBuilder sb = new StringBuilder();
		if (!date.isPresent()) {
			sb.append("Not specified.").append("\n");
		} else {
			sb.append(date.get().getDayOfMonth()).append(" ");
			sb.append(date.get().getMonth()).append(" ");
			sb.append(date.get().getYear()).append("\n");
		}
		return sb;
	}

	private StringBuilder getFormatedCompany(Optional<Company> company) {
		StringBuilder sb = new StringBuilder();
		if (!company.isPresent()) {
			sb.append("Not specified.").append("\n");
		} else {
			sb.append(company.get().getName()).append(" (#").append(company.get().getId()).append(")")
					.append("\n");
		}
		return sb;
	}
}
