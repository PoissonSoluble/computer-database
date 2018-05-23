package com.excilys.cdb.validation;

import com.excilys.cdb.model.User;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface IUserValidator {

    void validateUser(User authenticatedUser, User newUser) throws ValidationException;

}