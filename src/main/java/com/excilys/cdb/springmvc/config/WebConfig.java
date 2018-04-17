package com.excilys.cdb.springmvc.config;

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
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@Profile("webmvc")
@ComponentScan(basePackages = "com.excilys.cdb")
public class WebConfig implements WebMvcConfigurer {

    private final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);
    
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

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

    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
}