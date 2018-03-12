package com.excilys.cdb.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class DatabaseConnectionTest {

	@Test
	void testConnection() {
		assertNotNull(DatabaseConnection.INSTANCE.getConnection());
	}

}
