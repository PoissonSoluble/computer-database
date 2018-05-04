package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Component("computerModifier")
public class ComputerModifier implements CLIActionHandler {

    private IComputerService computerService;
    private CLIUserInputsAPI cliApi;

    public ComputerModifier(IComputerService pComputerService, CLIUserInputsAPI pCliApi) {
        computerService = pComputerService;
        cliApi = pCliApi;
    }
    
    @Override
    public boolean handle() {
        try {
            Computer computer = new Computer.Builder(cliApi.askID("computer")).build();
            if (!computerService.exists(computer)) {
                System.out.println("This computer does not exists.");
                return true;
            }
            computer = cliApi.askParametersForComputer(computer);
            updateComputer(computer);
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }

    private void updateComputer(Computer computer) {
        try {
            computerService.updateComputer(computer);
            System.out.println("Update completed.\n");
        } catch (NullNameException e) {
            System.out.println("The name is null.");
        } catch (InvalidDatesException e) {
            System.out.println("The dates are not valid.");
        } catch (NotExistingCompanyException e) {
            System.out.println("The company specified does not exist.");
        } catch (ValidationException e) {
            System.out.println("An error occured during the validation. Please check your parameters.");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }
    }
}
