package com.excilys.cdb.ui.actionhandlers;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

public class ComputerModifier implements CLIActionHandler {

    ComputerService service = ComputerService.INSTANCE;
    CLIComputerAPI cliApi = CLIComputerAPI.INSTANCE;

    @Override
    public void handle() {
        try {
            Long id = cliApi.askComputerID();
            if (!service.exists(id)) {
                System.out.println("This computer does not exists.");
                return;
            }
            Computer computer = cliApi.askParametersForComputer();
            checkNullAndUpdate(id, computer);
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
    }

    private void checkNullAndUpdate(Long id, Computer computer) {
        if (computer != null) {
            computer.setId(id);
            updateComputer(computer);
        }
    }

    private void updateComputer(Computer computer) {
        try {
            service.updateComputer(computer);
            System.out.println("Update completed.\n");
        } catch (NullNameException e) {
            System.out.println("The name is null.");
        } catch (InvalidDatesException e) {
            System.out.println("The dates are not valid.");
        } catch (NotExistingCompanyException e) {
            System.out.println("The company specified does not exist.");
        } catch (ValidationException e) {
            System.out.println("An error occured during the validation. Please check your parameters.");
        }
    }
}
