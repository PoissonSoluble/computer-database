package com.excilys.cdb.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger = LoggerFactory.getLogger(ComputerService.class);

	public void createComputer(Computer computer) throws ValidationException {
		validator.validateComputer(computer);
		dao.createComputer(computer);
		logger.info(new StringBuilder("Computer creation : ").append(computer).toString());
	}

	public void createComputer(String name, LocalDate introduced, LocalDate discontinued, Long company)
			throws ValidationException {
		Computer computer = initComputer(name, introduced, discontinued, company);
		createComputer(computer);
	}

	public void deleteComputer(Long id) {
		dao.deleteComputer(id);
		logger.info(new StringBuilder("Computer removal : ").append(id).toString());
	}

	public Computer detailComputer(Long id) {
		return dao.getComputer(id);
	}

	public boolean exists(Long id) {
		if (dao.getComputer(id) != null) {
			return true;
		}
		return false;
	}

	public int getComputerListPageTotalAmount(int pageSize) {
		return dao.getComputerListPageTotalAmount(pageSize);
	}

	public List<Computer> getComputerPage(int page, int pageSize) {
		try {
			return dao.listComputersByPage(page, pageSize);
		} catch (PageOutOfBoundsException e) {
			return null;
		}
	}

	public void updateComputer(Computer computer) throws ValidationException {
		validator.validateComputer(computer);
		dao.updateComputer(computer);
		logger.info(new StringBuilder("Computer update : ").append(computer).toString());
	}

	public void updateComputer(String name, LocalDate introduced, LocalDate discontinued, Long company)
			throws ValidationException {
		Computer computer = initComputer(name, introduced, discontinued, company);
		updateComputer(computer);
	}

	private Computer initComputer(String name, LocalDate introduced, LocalDate discontinued, Long companyId) {
		return new Computer.Builder(name).withIntroduced(introduced).withDiscontinued(discontinued)
				.withCompany(new Company.Builder(companyId).build()).build();
	}
}
