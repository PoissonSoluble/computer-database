package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.DatabaseConnection;

public class CompanyDAO {
	public static List<Company> listCompanies() {
		ArrayList<Company> companies = new ArrayList<>();
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				companies.add(CompanyMapper.createCompany(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}

	public static int getPageAmount(int pageSize) {
		int pages = 0, count;
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT count(id) as count FROM company");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			count = rs.getInt("count");
			pages = count / pageSize;
			pages += count % pageSize != 0 ? 1 : 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pages;
	}

	public static List<Company> listCompaniesByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Company> companies = new ArrayList<>();
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();) {
			stmt = conn.prepareStatement("SELECT * FROM company LIMIT ? OFFSET ?");
			stmt.setInt(1, pageSize);
			stmt.setInt(2, pageSize * (pageNumber - 1));
			rs = stmt.executeQuery();
			while (rs.next()) {
				companies.add(CompanyMapper.createCompany(rs));
			}
			if (companies.isEmpty()) {
				throw new PageOutOfBoundsException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(rs, stmt);
		}
		return companies;
	}
}
