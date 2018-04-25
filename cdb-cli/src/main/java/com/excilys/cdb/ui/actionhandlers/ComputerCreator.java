package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Component("computerCreator")
public class ComputerCreator implements CLIActionHandler {

    private IComputerService computerService;
    private CLIUserInputsAPI cliApi;

    public ComputerCreator(IComputerService pComputerService, CLIUserInputsAPI pCliApi) {
        computerService = pComputerService;
        cliApi = pCliApi;
    }
    
    @Override
    public boolean handle() {
        Computer computer = cliApi.askParametersForComputer(new Computer());
        if (computer != null) {
            createComputer(computer);
        }
        return true;
    }

    private void createComputer(Computer computer) {
        try {
            computerService.createComputer(computer);
            System.out.println("Creation completed.\n");
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
