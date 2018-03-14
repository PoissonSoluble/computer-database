package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public enum ComputerMapper {
	
	INSTANCE;
	
	private CompanyMapper companyMapper = CompanyMapper.INSTANCE;
	
	public Computer createComputer(ResultSet rs) {
		Computer computer = new Computer();
		try {
			computer.setId(rs.getLong("cu_id"));
			computer.setName(rs.getString("cu_name"));

			Date introduced = rs.getDate("cu_introduced");
			if (introduced != null) {
				computer.setIntroduced(introduced.toLocalDate());
			}
			
			Date discontinued = rs.getDate("cu_discontinued");
			if (discontinued != null) {
				computer.setDiscontinued(discontinued.toLocalDate());
			}
			Company company = companyMapper.createCompany(rs);
			computer.setCompany(company);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}
}
