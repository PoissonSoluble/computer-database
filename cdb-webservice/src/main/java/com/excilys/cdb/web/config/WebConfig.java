package com.excilys.cdb.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.excilys.cdb.config.ServiceConfig;

@Configuration
@Import(ServiceConfig.class)
@EnableWebMvc
@Profile("webmvc")
@ComponentScan("com.excilys.cdb.web")
public class WebConfig implements WebMvcConfigurer {

}