package com.excilys.cdb.mockdb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;

import com.excilys.cdb.persistence.DatabaseConnection;
import com.excilys.cdb.validation.ComputerValidatorTest;

public class MockDataBase {
    private static DatabaseConnection dbConn = DatabaseConnection.INSTANCE;
    private static final String INSERT_COMPANY = "INSERT INTO company (ca_name) VALUES(?);";
    private static final String INSERT_COMPUTER_SIMPLE = "INSERT INTO computer (cu_name) VALUES(?);";
    private static final String INSERT_COMPUTER_DATES = "INSERT INTO computer (cu_name, cu_introduced, cu_discontinued) VALUES(?,?,?);";
    private static final String INSERT_COMPUTER_COMPANY = "INSERT INTO computer (cu_name, ca_id) VALUES(?,?);";
    private static final String DROP_COMPANY = "DROP TABLE company;";
    private static final String DROP_COMPUTER = "DROP TABLE computer;";

    private static final String dateIntroduced = "1969-7-21";
    private static final String dateDiscontinued = "1995-7-21";

    public static void createDatabase() {
        initDataBase();
        for (int i = 1; i <= 20; i++) {
            createCompanyLine("Company " + i);
        }
        for (int i = 1; i <= 100; i++) {
            if (i <= 20) {
                createComputerLine("Computer " + i, (long) i);
            } else if (i <= 60) {
                createComputerLine("Computer " + i);
            } else {
                createComputerLine("Computer " + i, Date.valueOf(dateIntroduced), Date.valueOf(dateDiscontinued));
            }
        }
    }

    private static void createCompanyLine(String name) {
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPANY);) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private static void createComputerLine(String name) {
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_SIMPLE);) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private static void createComputerLine(String name, Date introduced, Date discontinued) {
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_DATES);) {
            stmt.setString(1, name);
            stmt.setDate(2, introduced);
            stmt.setDate(3, discontinued);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private static void createComputerLine(String name, Long companyId) {
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_COMPANY);) {
            stmt.setString(1, name);
            stmt.setLong(2, companyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private static void initDataBase() {
        try (Connection conn = dbConn.getConnection();) {
            SqlFile sqlFile = new SqlFile(
                    new File(ComputerValidatorTest.class.getClassLoader().getResource("db/1-SCHEMA.sql").toURI()));
            sqlFile.setConnection(conn);
            sqlFile.execute();
        } catch (SQLException | IOException | URISyntaxException | SqlToolError e) {
        }
    }

    public static void removeDataBase() {
        try (Connection conn = dbConn.getConnection();
                PreparedStatement dropCompany = conn.prepareStatement(DROP_COMPANY);
                PreparedStatement dropComputer = conn.prepareStatement(DROP_COMPUTER);) {
            dropCompany.executeUpdate();
            dropComputer.executeQuery();
        } catch (SQLException e) {
        }
    }
}
