package com.excilys.cdb.ui.actionhandlers;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

public class ComputerCreator implements CLIActionHandler {

	ComputerService service = ComputerService.INSTANCE;
	CLIComputerFiller filler = CLIComputerFiller.INSTANCE;
	
	@Override
	public void handle() {
		Computer computer = filler.askParametersForComputer();
		if(computer != null) {
			createComputer(computer);
		}
	}

	private void createComputer(Computer computer) {
		try {
			service.createComputer(computer);
			System.out.println("Creation completed !");
		} catch (NullNameException e) {
			System.err.println("The name is null.");
		} catch (InvalidDatesException e) {
			System.err.println("The dates are not valid.");
		} catch (NotExistingCompanyException e) {
			System.err.println("The company specified does not exist.");
		} catch (ValidationException e) {
			System.err.println("An error occured during the validation. Please check your parameters.");
		}
	}
}
