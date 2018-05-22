package com.excilys.cdb.web.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.service.IUserAuthenticationService;
import com.excilys.cdb.service.ServiceException;

@RestController
@RequestMapping("/users")
final class PublicUsersController {
    private IUserAuthenticationService authenticationService;

    public PublicUsersController(IUserAuthenticationService pAuthenticationService) {
        authenticationService = pAuthenticationService;
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") final String username,
            @RequestParam("password") final String password) {
        try {
            return authenticationService.login(username, password)
                    .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
        } catch (ServiceException e) {
            return e.getMessage();
        }
    }
}