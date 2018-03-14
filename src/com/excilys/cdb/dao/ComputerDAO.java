package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DatabaseConnection;

public enum ComputerDAO {

	INSTANCE;

	private DatabaseConnection dbConn = DatabaseConnection.INSTANCE;
	private ComputerMapper mapper = ComputerMapper.INSTANCE;

	public void createComputer(Computer computer) {
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO computer (cu_name, cu_introduced, cu_discontinued, ca_id) VALUES(?,?,?,?)");) {
			setParameters(computer, stmt);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteComputer(long id) {
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM computer WHERE cu_id = ?");) {
			stmt.setLong(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Computer getComputer(Long id) {
		Computer computer = null;
		ResultSet rs = null;
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn
						.prepareStatement("SELECT * FROM computer LEFT JOIN company USING(ca_id) WHERE cu_id = ?");) {
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			computer = retrieveComputerFromQuery(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeResultSet(rs);
		}
		return computer;
	}

	public int getComputerListPageTotalAmount(int pageSize) {
		int pages = 0;
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT count(cu_id) as count FROM computer");
				ResultSet rs = stmt.executeQuery();) {
			rs.next();
			pages = computePageAmountFromQuery(pageSize, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pages;
	}

	public List<Computer> listComputers() {
		ArrayList<Computer> computers = new ArrayList<>();
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM computer LEFT JOIN company USING(ca_id)");
				ResultSet rs = stmt.executeQuery();) {
			retrieveComputersFromQuery(computers, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public List<Computer> listComputersByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException {
		ResultSet rs = null;
		ArrayList<Computer> computers = new ArrayList<>();
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"SELECT * FROM computer LEFT JOIN company USING(ca_id) ORDER BY cu_id LIMIT ? OFFSET ?");) {
			retrieveParametersForComputerPage(pageNumber, pageSize, stmt);
			rs = stmt.executeQuery();
			retrievePageContentFromQueryResult(rs, computers);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeResultSet(rs);
		}
		return computers;
	}

	public void updateComputer(Computer computer) {
		try (Connection conn = dbConn.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"UPDATE computer SET cu_name = ?, cu_introduced = ?, cu_discontinued = ?, ca_id = ? WHERE cu_id = ?");) {
			setParameters(computer, stmt);
			stmt.setLong(5, computer.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addDateToStatement(int parameterIndex, LocalDate date, PreparedStatement stmt) throws SQLException {
		if (date != null) {
			stmt.setDate(parameterIndex, Date.valueOf(date));
		} else {
			stmt.setNull(parameterIndex, java.sql.Types.DATE);
		}
	}

	private int computePageAmountFromQuery(int pageSize, ResultSet rs) throws SQLException {
		int pages, count;
		count = rs.getInt("count");
		pages = count / pageSize;
		pages += count % pageSize != 0 ? 1 : 0;
		return pages;
	}

	private Computer retrieveComputerFromQuery(ResultSet rs) throws SQLException {
		if(rs.next()) {
			return mapper.createComputer(rs);
		}
		return null;
	}

	private void retrieveComputersFromQuery(ArrayList<Computer> computers, ResultSet rs) throws SQLException {
		while (rs.next()) {
			computers.add(mapper.createComputer(rs));
		}
	}

	private void retrievePageContentFromQueryResult(ResultSet rs, ArrayList<Computer> computers)
			throws SQLException, PageOutOfBoundsException {
		retrieveComputersFromQuery(computers, rs);
		if (computers.isEmpty()) {
			throw new PageOutOfBoundsException();
		}
	}

	private void retrieveParametersForComputerPage(int pageNumber, int pageSize, PreparedStatement stmt)
			throws SQLException {
		stmt.setInt(1, pageSize);
		stmt.setInt(2, pageSize * (pageNumber - 1));
	}

	private void setParameters(Computer computer, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, computer.getName());
		addDateToStatement(2, computer.getIntroduced(), stmt);
		addDateToStatement(3, computer.getDiscontinued(), stmt);
		if (computer.getCompany() != null && computer.getCompany().getId() != null)
			stmt.setLong(4, computer.getCompany().getId());
		else {
			stmt.setNull(4, java.sql.Types.BIGINT);
		}
	}
}
