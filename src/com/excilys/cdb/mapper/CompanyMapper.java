package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public enum CompanyMapper {
	
	INSTANCE;
	
	public Company createCompany(ResultSet rs) {
		Company company = new Company();
		try {
			company.setId(rs.getLong("ca_id"));
			company.setName(rs.getString("ca_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
	
}
