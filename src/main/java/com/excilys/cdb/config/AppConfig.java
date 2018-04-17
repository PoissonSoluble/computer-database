package com.excilys.cdb.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("cli")
@ComponentScan({ "com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.dao",
        "com.excilys.cdb.validation", "com.excilys.cdb.service", "com.excilys.cdb.ui", "com.excilys.cdb.mockdb" })
public class AppConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public DataSource dataSource() {
        Properties properties = new Properties();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            dataSource.setDriverClassName(properties.getProperty("dbdriver"));
            dataSource.setUrl(properties.getProperty("dburl"));
            dataSource.setUsername(properties.getProperty("dbid"));
            dataSource.setPassword(properties.getProperty("dbpassword"));
            return dataSource;
        } catch (IOException e) {
            LOGGER.error("dataSource() {}", e);
            return dataSource;
        }
    }
}
