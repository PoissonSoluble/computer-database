package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DatabaseConnection;

public class CompanyDAO {
	public static List<Company> getAllCompanies(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseConnection.INSTANCE.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM company");
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DatabaseConnection.INSTANCE.closeConnection(conn, rs, stmt);
		}
		
		return new ArrayList<>();
	}
}
