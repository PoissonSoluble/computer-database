package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.cdb.config.DataSourceConfig;

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan({"com.excilys.cdb.service", "com.excilys.cdb.validation"})
public class ServiceConfig {

}
