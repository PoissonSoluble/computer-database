package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Computer;

public enum ComputerMapper {
	
	INSTANCE;
	
	public Computer createComputer(ResultSet rs) {
		Computer computer = new Computer();
		try {
			computer.setId(rs.getLong("id"));
			computer.setName(rs.getString("name"));

			Date introduced = rs.getDate("introduced");
			if (introduced != null) {
				computer.setIntroduced(introduced.toLocalDate());
			}
			
			Date discontinued = rs.getDate("discontinued");
			if (discontinued != null) {
				computer.setDiscontinued(discontinued.toLocalDate());
			}
			
			computer.setCompany(rs.getLong("company_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}
}
