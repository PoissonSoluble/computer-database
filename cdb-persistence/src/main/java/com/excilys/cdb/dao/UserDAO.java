package com.excilys.cdb.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.excilys.cdb.model.User;

public interface UserDAO  extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
