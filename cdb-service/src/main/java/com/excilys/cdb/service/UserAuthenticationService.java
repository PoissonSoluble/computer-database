package com.excilys.cdb.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;
import com.excilys.cdb.validation.IUserValidator;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private Map<String, User> users = new HashMap<>();
    private UserDAO userDAO;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(9);
    private IUserValidator userValidator;

    public UserAuthenticationService(UserDAO pUserDAO, IUserValidator pUserValidator) {
        userDAO = pUserDAO;
        userValidator = pUserValidator;
    }

    @Override
    public Optional<String> login(final String username, final String password) throws ServiceException {
        String token = UUID.randomUUID().toString();
        User user = userDAO.findByLogin(username).orElseThrow(() -> new ServiceException("This user does not exist."));
        if (!encoder.matches(password, user.getPassword())) {
            throw new ServiceException("Wrong password.");
        }
        user.setToken(token);
        users.put(token, user);
        return Optional.of(token);
    }

    @Override
    public Optional<User> findByToken(final String token) {
        return Optional.ofNullable(users.get(token));
    }

    @Override
    public void logout(final User user) {
        users.remove(user.getToken());
    }

    @Override
    public void register(User authenticatedUser, User newUser) throws ServiceException, ValidationException {
        userValidator.validateUser(authenticatedUser, newUser);
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        try {
            userDAO.save(newUser);
        } catch (IllegalArgumentException e) {
            throw new ServiceException("The new user is not valid.");
        }
    }

}