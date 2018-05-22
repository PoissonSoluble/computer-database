package com.excilys.cdb.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private Map<String, User> users = new HashMap<>();
    private UserDAO userDAO;

    public UserAuthenticationService(UserDAO pUserDAO) {
        userDAO = pUserDAO;
    }
    
    @Override
    public Optional<String> login(final String username, final String password) throws ServiceException {
        String token = UUID.randomUUID().toString();
        User user = userDAO.findByLogin(username).orElseThrow(() -> new ServiceException("This user does nopt exist."));
        if(!new BCryptPasswordEncoder(9).matches(password, user.getPassword())) {
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
}