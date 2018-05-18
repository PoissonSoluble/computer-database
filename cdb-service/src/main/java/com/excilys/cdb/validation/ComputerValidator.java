package com.excilys.cdb.validation;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Component("computerValidator")
public class ComputerValidator implements IComputerValidator {

    private CompanyDAO companyDAO;
    
    public ComputerValidator(CompanyDAO pCompanyDAO) {
        companyDAO = pCompanyDAO;
    }   

    @Override
    public void validateComputer(Computer computer) throws ValidationException {
        validateName(computer.getName());
        validateDates(computer.getIntroduced(), computer.getDiscontinued());
        validateCompany(computer.getCompany());
    }

    private void validateCompany(Optional<Company> companyOpt) throws ValidationException {
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            if (company.getId().isPresent() && !companyDAO.findById(company.getId().get()).isPresent()) {
                throw new NotExistingCompanyException();
            }
        }
    }

    private void validateDates(Optional<LocalDate> introduced, Optional<LocalDate> discontinued)
            throws InvalidDatesException {
        if (discontinued.isPresent() && introduced.isPresent() && introduced.get().isAfter(discontinued.get())) {
            throw new InvalidDatesException();
        }
    }

    private void validateName(Optional<String> name) throws NullNameException {
        if (!name.isPresent()) {
            throw new NullNameException();
        }
    }
}
