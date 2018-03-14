package com.excilys.cdb.validation;

import java.time.LocalDate;

import com.excilys.cdb.dao.CompanyDAO;
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
	
	private void validateName(String name) throws NullNameException{
		if(name == null) {
			throw new NullNameException();
		}
	}
	
	private void validateDates(LocalDate introduced, LocalDate discontinued) throws InvalidDatesException {
		if(discontinued != null && introduced != null && introduced.isAfter(discontinued)) {
			throw new InvalidDatesException();
		}
	}
	
	private void validateCompany(Company company) throws NotExistingCompanyException {
		if(company.getId() != null && companyDAO.getCompany(company.getId()) == null) {
			throw new NotExistingCompanyException();
		}
	}
}
