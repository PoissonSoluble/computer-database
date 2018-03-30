package com.excilys.cdb.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

public enum DatabaseConnection {
    INSTANCE;

    private HikariDataSource dataSource;
    private final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);

    private DatabaseConnection() {
        LOGGER.info("Init Hikari connection pool");
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(prop.getProperty("dburl", getUrl(prop)));
            dataSource.setUsername(prop.getProperty("dbid"));
            dataSource.setPassword(prop.getProperty("dbpassword"));
        } catch (IOException e) {
            LOGGER.error("Error while initiating Hikari connection pool. {}", e);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        Properties prop = new Properties();
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            Class.forName(prop.getProperty("dbdriver"));
            conn = dataSource.getConnection();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Error while retrieving a SQL connection from the pool. {}", e);
        }
        return conn;
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
