package com.excilys.cdb.persistence;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public enum DatabaseConnection {
	INSTANCE;

	public Connection getConnection() {
		Connection conn = null;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream("config.properties"));

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(getUrl(prop), prop.getProperty("dbid"), prop.getProperty("dbpassword"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeResultSet(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void closeConnection(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getUrl(Properties prop) {
		StringBuilder sb = new StringBuilder("jdbc:mysql://");
		sb.append(prop.getProperty("dbserver"));
		sb.append(":");
		sb.append(prop.getProperty("dbport"));
		sb.append("/");
		sb.append(prop.getProperty("dbname"));
		sb.append("?useSSL=false");
		return sb.toString();
	}

}
