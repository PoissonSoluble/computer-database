package com.excilys.cdb.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Computer;

public enum ComputerService {
	INSTANCE;
	
	private ComputerDAO dao = ComputerDAO.INSTANCE;
	
	public List<Computer> getComputerPage(int page, int pageSize){
		try {
			return dao.listComputersByPage(page, pageSize);
		} catch (PageOutOfBoundsException e) {
			return null;
		}
	}
	
	public boolean createComputer(String name, LocalDate introduced, LocalDate discontinued, Long company){
		try {
			dao.createComputer(initComputer(name, introduced, discontinued, company));
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean updateComputer(String name, LocalDate introduced, LocalDate discontinued, Long company){
		try {
			dao.updateComputer(initComputer(name, introduced, discontinued, company));
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	private Computer initComputer(String name, LocalDate introduced, LocalDate discontinued, Long company) {
		Computer computer = new Computer();
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);
		return computer;
	}
}

