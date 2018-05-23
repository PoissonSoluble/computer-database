package com.excilys.cdb.service;

import java.util.Optional;

import com.excilys.cdb.model.User;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface IUserAuthenticationService {

    Optional<String> login(String username, String password) throws ServiceException;

    Optional<User> findByToken(String token);

    void logout(User user);

    void register(User authenticatedUser, User newUser) throws ServiceException, ValidationException;

}