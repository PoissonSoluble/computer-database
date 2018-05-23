package com.excilys.cdb.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.UserDTO;
import com.excilys.cdb.mapper.IUserDTOMapper;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.IUserAuthenticationService;

@RestController
@CrossOrigin
@RequestMapping("/users")
final class SecuredUsersController {

    private IUserAuthenticationService authenticationService;
    private IUserDTOMapper userDtoMapper;

    public SecuredUsersController(IUserAuthenticationService pAuthenticationService, IUserDTOMapper pUserDtoMapper) {
        authenticationService = pAuthenticationService;
        userDtoMapper = pUserDtoMapper;
    }

    @GetMapping("/current")
    public UserDTO getCurrent(@AuthenticationPrincipal final User user) {
        user.setPassword("");
        return userDtoMapper.userToDto(user);
    }

    @PostMapping("/logout")
    public boolean logout(@AuthenticationPrincipal final User user) {
        authenticationService.logout(user);
        return true;
    }
}