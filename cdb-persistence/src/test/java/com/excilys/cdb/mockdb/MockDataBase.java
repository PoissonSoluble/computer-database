package com.excilys.cdb.mockdb;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mockDataBase")
public class MockDataBase {
    @Autowired
    private DataSource dataSource;
    
    private static final String INSERT_COMPANY = "INSERT INTO company (ca_name) VALUES(?);";
    private static final String INSERT_COMPUTER_SIMPLE = "INSERT INTO computer (cu_name) VALUES(?);";
    private static final String INSERT_COMPUTER_DATES = "INSERT INTO computer (cu_name, cu_introduced, cu_discontinued) VALUES(?,?,?);";
    private static final String INSERT_COMPUTER_COMPANY = "INSERT INTO computer (cu_name, ca_id) VALUES(?,?);";
    private static final String DROP_COMPANY = "DROP TABLE company;";
    private static final String DROP_COMPUTER = "DROP TABLE computer;";

    private static final String dateIntroduced = "1969-7-21";
    private static final String dateDiscontinued = "1995-7-21";

    public void createDatabase() {
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

    private void createCompanyLine(String name) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPANY);) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private void createComputerLine(String name) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_SIMPLE);) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private void createComputerLine(String name, Date introduced, Date discontinued) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_DATES);) {
            stmt.setString(1, name);
            stmt.setDate(2, introduced);
            stmt.setDate(3, discontinued);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private void createComputerLine(String name, Long companyId) {
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT_COMPUTER_COMPANY);) {
            stmt.setString(1, name);
            stmt.setLong(2, companyId);
            stmt.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private void initDataBase() {
        try (Connection conn = getConnection();) {
            SqlFile sqlFile = new SqlFile(
                    new File(MockDataBase.class.getClassLoader().getResource("db/1-SCHEMA.sql").toURI()));
            sqlFile.setConnection(conn);
            sqlFile.execute();
        } catch (SQLException | IOException | URISyntaxException | SqlToolError e) {
        }
    }

    public void removeDataBase() {
        try (Connection conn = getConnection();
                PreparedStatement dropCompany = conn.prepareStatement(DROP_COMPANY);
                PreparedStatement dropComputer = conn.prepareStatement(DROP_COMPUTER);) {
            dropCompany.executeUpdate();
            dropComputer.executeQuery();
        } catch (SQLException e) {
        }
    }
    
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
