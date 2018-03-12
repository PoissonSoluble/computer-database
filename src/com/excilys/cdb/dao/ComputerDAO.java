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
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Computer> computers = new ArrayList<>();
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();) {
			stmt = conn.prepareStatement("SELECT * FROM computer LIMIT ? OFFSET ?");
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
			DatabaseConnection.INSTANCE.closeConnection(rs, stmt);
		}
		return computers;
	}

	public static void createComputer(Computer computer) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try (Connection conn = DatabaseConnection.INSTANCE.getConnection();) {
			stmt = conn.prepareStatement(
					"INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?,?,?,?)");
			stmt.setString(1, computer.getName());
			stmt.setDate(2, Date.valueOf(computer.getIntroduced()));
			stmt.setDate(3, Date.valueOf(computer.getDiscontinued()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DatabaseConnection.INSTANCE.closeConnection(rs, stmt);
		}
	}

	public static void main(String[] args) {
	}
}
