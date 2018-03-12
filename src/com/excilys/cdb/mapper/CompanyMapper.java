package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.Company;

public class CompanyMapper {
	public static Company createCompany(ResultSet rs) {
		Company company = new Company();
		try {
			company.setId(rs.getLong("id"));
			company.setName(rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}
	
}
