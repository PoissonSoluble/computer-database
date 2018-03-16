package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public enum CompanyMapper {
	
	INSTANCE;
	
	public Company createCompany(ResultSet rs) {
		Company.Builder companyBuilder = new Company.Builder();
		try {
			companyBuilder.withId(rs.getLong("ca_id"));
			companyBuilder.withName(rs.getString("ca_name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyBuilder.build();
	}
	
}
