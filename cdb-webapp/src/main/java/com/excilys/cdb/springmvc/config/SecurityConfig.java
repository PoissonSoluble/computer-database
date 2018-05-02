package com.excilys.cdb.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.excilys.cdb.service.IUserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private UserDetailsService userService;
    
    public SecurityConfig(IUserService pUserService) {
        userService = pUserService;
    }
    

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(9);
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**"); // allows login to access to the resources
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/computer/add", "/computer/edit","/computer/delete").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/computer/dashboard", "/**").permitAll()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/computer/dashboard")
                .and()
                .logout().and().csrf();
    }

}
