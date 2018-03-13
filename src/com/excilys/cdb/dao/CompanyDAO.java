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

public enum CompanyDAO {
	
	INSTANCE;
	
	private DatabaseConnection dbConn = DatabaseConnection.INSTANCE;
	private CompanyMapper mapper = CompanyMapper.INSTANCE;
	
	public Company getCompany(Company company) {
		return getCompany(company.getId());
	}
	
	public Company getCompany(Long id) {
		ResultSet rs = null;
		Company company = null;
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company WHERE id = ?");) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			company = retrieveCompanyFromQuery(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dbConn.closeResultSet(rs);
		}
		return company;
	}
	
	public int getPageAmount(int pageSize) {
		int pages = 0;
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT count(id) as count FROM company");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			pages = computePageAmountFromQuery(pageSize, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pages;
	}

	public List<Company> listCompanies() {
		ArrayList<Company> companies = new ArrayList<>();
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				companies.add(mapper.createCompany(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}

	public List<Company> listCompaniesByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException {
		ResultSet rs = null;
		ArrayList<Company> companies = new ArrayList<>();
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM company LIMIT ? OFFSET ?");) {
			retrieveParametersForComputerPage(pageNumber, pageSize, stmt);
			rs = stmt.executeQuery();
			retrievePageContentFromQueryResult(rs, companies);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeResultSet(rs);
		}
		return companies;
	}

	private int computePageAmountFromQuery(int pageSize, ResultSet rs) throws SQLException {
		int pages, count;
		count = rs.getInt("count");
		pages = count / pageSize;
		pages += count % pageSize != 0 ? 1 : 0;
		return pages;
	}

	private Company retrieveCompanyFromQuery(ResultSet rs) throws SQLException {
		rs.next();
		Company company = mapper.createCompany(rs);
		return company;
	}

	private void retrievePageContentFromQueryResult(ResultSet rs, ArrayList<Company> companies)
			throws SQLException, PageOutOfBoundsException {
		while (rs.next()) {
			companies.add(mapper.createCompany(rs));
		}
		if (companies.isEmpty()) {
			throw new PageOutOfBoundsException();
		}
	}

	private void retrieveParametersForComputerPage(int pageNumber, int pageSize, PreparedStatement stmt)
			throws SQLException {
		stmt.setInt(1, pageSize);
		stmt.setInt(2, pageSize * (pageNumber - 1));
	}
}
