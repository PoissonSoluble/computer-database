package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DatabaseConnection;

public class ComputerDAO {
	public static List<Computer> listComputers() {
		ArrayList<Computer> computers = new ArrayList<>();
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT * FROM computer");
				ResultSet rs = stmt.executeQuery();) {
			while (rs.next()) {
				computers.add(ComputerMapper.createComputer(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	public static int getPageAmount(int pageSize) {
		int pages = 0, count;
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement("SELECT count(id) as count FROM computer");
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

	public static List<Computer> listComputersByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException {
		ResultSet rs = null;
		ArrayList<Computer> computers = new ArrayList<>();
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM computer LIMIT ? OFFSET ?");){
			stmt.setInt(1, pageSize);
			stmt.setInt(2, pageSize * (pageNumber - 1));
			rs = stmt.executeQuery();
			while (rs.next()) {
				computers.add(ComputerMapper.createComputer(rs));
			}
			if (computers.isEmpty()) {
				throw new PageOutOfBoundsException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeResultSet(rs);
		}
		return computers;
	}

	public static void createComputer(Computer computer) throws SQLException{
		if(computer.getName() == null) {
			throw new SQLException("Name cannot be null.");
		}
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)");) {
			setParameters(computer, stmt);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteComputer(long id) {
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM computer WHERE id = ?");) {
			stmt.setLong(1, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateComputer(Computer computer) throws SQLException{
		if(computer.getName() == null || computer.getId() == null) {
			throw new SQLException("Name and id cannot be null.");
		}
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();
				PreparedStatement stmt = conn.prepareStatement(
						"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");) {
			setParameters(computer, stmt);
			stmt.setLong(5, computer.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void setParameters(Computer computer, PreparedStatement stmt) throws SQLException {
		stmt.setString(1, computer.getName());
		if (computer.getIntroduced() != null) {
			stmt.setDate(2, Date.valueOf(computer.getIntroduced()));
		} else {
			stmt.setNull(2, java.sql.Types.DATE);
		}
		if (computer.getDiscontinued() != null) {
			stmt.setDate(3, Date.valueOf(computer.getDiscontinued()));
		} else {
			stmt.setNull(3, java.sql.Types.DATE);
		}
		if(computer.getCompany() != null)
			stmt.setLong(4, computer.getCompany());
		else {
			stmt.setNull(4, java.sql.Types.BIGINT);
		}
	}
}
