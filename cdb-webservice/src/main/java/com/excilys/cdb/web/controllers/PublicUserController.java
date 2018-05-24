package com.excilys.cdb.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.UserDTO;
import com.excilys.cdb.mapper.IUserDTOMapper;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.IUserAuthenticationService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.ValidationException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@Api
@RequestMapping("/users")
final class PublicUsersController {
    private IUserAuthenticationService authenticationService;
    private IUserDTOMapper userDtoMapper;

    public PublicUsersController(IUserAuthenticationService pAuthenticationService, IUserDTOMapper pUserDtoMapper) {
        authenticationService = pAuthenticationService;
        userDtoMapper = pUserDtoMapper;
    }

    @ApiOperation(value = "Loging user and returns token", response = String.class)
    @PostMapping("/login")
    public String login(@RequestParam("login") final String username,
            @RequestParam("password") final String password) {
        try {
            return authenticationService.login(username, password)
                    .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
        } catch (ServiceException e) {
            return e.getMessage();
        }
    }

    @ApiOperation(value = "Register a new user", response = String.class)
    @PostMapping
    public String register(@AuthenticationPrincipal User authenticatedUser, @RequestBody UserDTO newUser) {
        try {
            authenticationService.register(authenticatedUser, userDtoMapper.dtoToUser(newUser));
            return "OK.";
        } catch (ServiceException | ValidationException e) {
            return e.getMessage();
        }
    }
}