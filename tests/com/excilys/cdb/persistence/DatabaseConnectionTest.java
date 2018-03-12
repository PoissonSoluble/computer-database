package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DatabaseConnectionTest {
	
	private Connection conn;
	
	@BeforeEach
	void setUp() {
		conn = DatabaseConnection.INSTANCE.getConnection();
	}
	
	@Test
	void testConnection() {
		assertNotNull(conn);
	}
	
	@AfterEach
	void unSetUp(){
		DatabaseConnection.INSTANCE.closeConnection(conn);
	}

}
