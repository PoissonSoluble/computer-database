package com.excilys.cdb.validation;

import java.time.LocalDate;

import com.excilys.cdb.dao.CompanyDAO;
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
	
	public void validateName(String name) throws NullNameException{
		if(name == null) {
			throw new NullNameException();
		}
	}
	
	public void validateDates(LocalDate introduced, LocalDate discontinued) throws InvalidDatesException {
		if(introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			throw new InvalidDatesException();
		}
	}
	
	public void validateCompany(Long companyId) throws NotExistingCompanyException {
		if(companyDAO.getCompany(companyId) == null) {
			throw new NotExistingCompanyException();
		}
	}
}
