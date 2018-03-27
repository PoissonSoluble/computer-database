package com.excilys.cdb.persistence;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

public class DatabaseConnectionTest {

    public DatabaseConnectionTest() {
        super();
    }

    @Test
    public void testConnection() throws SQLException {
        try(Connection conn = DatabaseConnection.INSTANCE.getConnection();){
            assertNotNull(conn);
        }
    }
}
