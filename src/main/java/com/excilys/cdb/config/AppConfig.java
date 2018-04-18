package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Profile("cli")
@PropertySource("classpath:config.properties")
@ComponentScan({ "com.excilys.cdb.config", "com.excilys.cdb.mapper", "com.excilys.cdb.dao",
        "com.excilys.cdb.validation", "com.excilys.cdb.service", "com.excilys.cdb.ui", "com.excilys.cdb.mockdb" })
public class AppConfig {

    @Value("${dbdriver}")
    private String dbDriver;

    @Value("${dburl}")
    private String dbUrl;

    @Value("${dbid}")
    private String dbId;

    @Value("${dbpassword}")
    private String dbPassword;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbId);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }
}
