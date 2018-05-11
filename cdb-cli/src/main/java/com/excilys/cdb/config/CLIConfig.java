package com.excilys.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cli")
@Import(BindingConfig.class)
@ComponentScan("com.excilys.cdb.ui")
public class CLIConfig {

}
