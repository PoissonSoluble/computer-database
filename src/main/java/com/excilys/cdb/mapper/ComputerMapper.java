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
		Computer.Builder computerBuilder = new Computer.Builder();
		try {
			computerBuilder.withId(rs.getLong("cu_id"));
			computerBuilder.withName(rs.getString("cu_name"));

			Date introduced = rs.getDate("cu_introduced");
			if (introduced != null) {
				computerBuilder.withIntroduced(introduced.toLocalDate());
			}
			
			Date discontinued = rs.getDate("cu_discontinued");
			if (discontinued != null) {
				computerBuilder.withDiscontinued(discontinued.toLocalDate());
			}
			Company company = companyMapper.createCompany(rs);
			computerBuilder.withCompany(company);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerBuilder.build();
	}
}
