package com.excilys.cdb.validation;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

public enum ComputerValidator {
    INSTANCE;

    private CompanyDAO companyDAO = CompanyDAO.INSTANCE;

    public void validateComputer(Computer computer) throws ValidationException {
        validateName(computer.getName());
        validateDates(computer.getIntroduced(), computer.getDiscontinued());
        validateCompany(computer.getCompany());
    }

    private void validateCompany(Optional<Company> company) throws ValidationException {
        try {
            if (company.isPresent() && !companyDAO.getCompany(company.get().getId()).isPresent()) {
                throw new NotExistingCompanyException();
            }
        } catch (DAOException e) {
            throw new ValidationException();
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
