package com.excilys.cdb.ui.actionhandlers;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.ui.CLIException;
import com.excilys.cdb.ui.CommandLineInterface;

@Component("computerItemizer")
public class ComputerItemizer implements CLIActionHandler {

    @Autowired
    private IComputerService service;
    @Autowired
    private CLIUserInputsAPI cliApi;

    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("computer");
            printComputer(service.getComputer(id));
            CommandLineInterface.getUserInput();
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        } catch (NoSuchElementException e) {
            System.out.println("This computer does not exists.");
        }
        return true;
    }

    private String constructDetailString(Computer computer) throws CLIException {
        StringBuilder sb = new StringBuilder("\n== Details of computer#")
                .append(computer.getId().orElseThrow(() -> new CLIException("Computer cannot be printed without ID")))
                .append(" ==\n");
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

    private StringBuilder getFormatedCompany(Optional<Company> company) {
        StringBuilder sb = new StringBuilder();
        if (!company.isPresent()) {
            sb.append("Not specified.").append("\n");
        } else {
            sb.append(company.get().getName()).append(" (#").append(company.get().getId()).append(")").append("\n");
        }
        return sb;
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

    private void printComputer(Optional<Computer> computer) {
        if (!computer.isPresent()) {
            System.out.println("This computer does not exist.");
        } else {
            try {
                System.out.println(constructDetailString(computer.get()));
            } catch (CLIException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
