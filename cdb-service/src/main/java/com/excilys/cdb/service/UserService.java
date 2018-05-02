package com.excilys.cdb.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.UserDAO;
import com.excilys.cdb.model.User;

@Service("userService")
public class UserService implements IUserService {

    private UserDAO userDAO;

    public UserService(UserDAO pUserDAO) {
        userDAO = pUserDAO;
    }

    @Override
    public User loadUserByUsername(String name) throws UsernameNotFoundException {
        return userDAO.findByLogin(name).orElseThrow(() -> new UsernameNotFoundException("This user does not exist"));
    }

}
