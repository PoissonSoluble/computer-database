package com.excilys.cdb.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.model.User;
import com.excilys.cdb.service.IUserAuthenticationService;

@RestController
@RequestMapping("/users")
final class SecuredUsersController {
    
  private IUserAuthenticationService authenticationService;
  
  public SecuredUsersController(IUserAuthenticationService pAuthenticationService) {
      authenticationService = pAuthenticationService;
  }

  @GetMapping("/current")
  public User getCurrent(@AuthenticationPrincipal final User user) {   
    return user;
  }

  @GetMapping("/logout")
  public boolean logout(@AuthenticationPrincipal final User user) {
    authenticationService.logout(user);
    return true;
  }
}