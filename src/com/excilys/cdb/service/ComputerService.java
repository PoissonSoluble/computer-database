package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.List;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.ComputerValidator;
import com.excilys.cdb.validation.exceptions.ValidationException;

public enum ComputerService {
	INSTANCE;

	private ComputerDAO dao = ComputerDAO.INSTANCE;
	private ComputerValidator validator = ComputerValidator.INSTANCE;

	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Long company)
			throws ValidationException {
		Computer computer = initComputer(name, introduced, discontinued, company);
		createComputer(computer);
	}

	public void createComputer(Computer computer) throws ValidationException {
		validator.validateComputer(computer);
		dao.createComputer(computer);
	}

	public void deleteComputer(Long id) {
		dao.deleteComputer(id);
	}

	public Computer detailComputer(Long id) {
		return dao.getComputer(id);
	}

	public List<Computer> getComputerPage(int page, int pageSize) {
		try {
			return dao.listComputersByPage(page, pageSize);
		} catch (PageOutOfBoundsException e) {
			return null;
		}
	}

	public int getComputerListPageTotalAmount(int pageSize) {
		return dao.getComputerListPageTotalAmount(pageSize);
	}

	public void updateComputer(Computer computer) throws ValidationException {
		validator.validateComputer(computer);
		dao.updateComputer(computer);
	}
	
	public boolean exists(Long id) {
		if(dao.getComputer(id) != null) {
			return true;
		}
		return false;
	}

	public void updateComputer(String name, LocalDate introduced, LocalDate discontinued, Long company)
			throws ValidationException {
		Computer computer = initComputer(name, introduced, discontinued, company);
		updateComputer(computer);
	}

	private Computer initComputer(String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
		Computer computer = new Computer();
		Company company = new Company();
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		company.setId(companyId);
		computer.setCompany(company);
		return computer;
	}
}
