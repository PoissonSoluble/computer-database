package com.excilys.cdb.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@Import(BindingConfig.class)
@PropertySource("classpath:config.properties")
@ComponentScan({"com.excilys.cdb.dao", "com.excilys.cdb.mockdb"})
public class DataSourceConfig {

    @Value("${db.driver}")
    private String dbDriver;

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.id}")
    private String dbId;

    @Value("${db.password}")
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
