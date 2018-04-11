package com.excilys.cdb.ui.actionhandlers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.CommandLineInterface;

@Component("cliApi")
public class CLIUserInputsAPI {

    public Long askID(String type) throws NumberFormatException {
        System.out.print(new StringBuilder("Enter the ").append(type).append(" ID: ").toString());
        String stringId = CommandLineInterface.getUserInput();
        return Long.parseLong(stringId);
    }

    public Computer askParametersForComputer() {
        Computer computer = new Computer();
        if (!readName(computer) || !readDates(computer) || !readCompany(computer)) {
            return null;
        }
        return computer;
    }

    private Optional<Long> askCompany() throws NumberFormatException {
        System.out.println("Enter a company ID (ENTER to ignore): ");
        String input = CommandLineInterface.getUserInput();
        if (input.equals("")) {
            return Optional.empty();
        } else {
            return Optional.of(Long.parseLong(input));
        }
    }

    private Optional<LocalDate> askDate() throws DateTimeParseException {
        System.out.print("Enter a date (format 'yyyy-mm-dd', ENTER to ignore): ");
        String input = CommandLineInterface.getUserInput();
        if (input.equals("")) {
            return Optional.empty();
        } else {
            return Optional.of(LocalDate.parse(input));
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
            Optional<Long> company = askCompany();
            if (company.isPresent()) {
                computer.setCompany(new Company.Builder(company.get()).build());
            }
        } catch (NumberFormatException e) {
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
        if (computer.getIntroduced().isPresent()) {
            System.out.print("Discontinued date. ");
            Optional<LocalDate> discontinued = askDate();
            if (discontinued.isPresent()) {
                computer.setDiscontinued(discontinued.get());
            }
        }
    }

    private void readIntroduced(Computer computer) {
        System.out.print("Introduced date. ");
        Optional<LocalDate> introduced = askDate();
        if (introduced.isPresent()) {
            computer.setIntroduced(introduced.get());
        }
    }

    private boolean readName(Computer computer) {
        computer.setName(askName());
        if (!computer.getName().isPresent()) {
            System.out.println("The name cannot be null.");
            return false;
        }
        return true;
    }

}
