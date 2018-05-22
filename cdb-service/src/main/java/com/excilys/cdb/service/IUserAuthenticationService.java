package com.excilys.cdb.service;

import java.util.Optional;

import com.excilys.cdb.model.User;

public interface IUserAuthenticationService {

    Optional<String> login(String username, String password) throws ServiceException;

    Optional<User> findByToken(String token);

    void logout(User user);

}