package com.excilys.cdb.validation;

import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.User;
import com.excilys.cdb.model.UserRole;
import com.excilys.cdb.validation.exceptions.InvalidLoginException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Component("userValidator")
public class UserValidator implements IUserValidator {

    @Override
    public void validateUser(User authenticatedUser, User newUser) throws ValidationException {
        validateLogin(ofNullable(newUser.getLogin()));
        validateNotNull(ofNullable(newUser.getPassword()));
        validateRole(authenticatedUser, newUser.getRole());
    }

    private void validateLogin(Optional<String> login) throws NullNameException, InvalidLoginException {
        validateNotNull(login);
        if (!Pattern.matches("[a-zA-Z0-9-_]+", login.get())) {
            throw new InvalidLoginException();
        }
    }

    private void validateNotNull(Optional<String> string) throws NullNameException {
        if (!string.isPresent()) {
            throw new NullNameException();
        }
    }

    private void validateRole(User authenticatedUser, UserRole role) {
        if (!ofNullable(role).isPresent() || !ofNullable(role.getId()).isPresent()) {
            role = UserRole.Role.USER.getRole();
        } else if (ofNullable(authenticatedUser).isPresent() && ofNullable(authenticatedUser.getRole()).isPresent()
                && !authenticatedUser.getRole().equals(UserRole.Role.ADMIN.getRole())) {
            role = UserRole.Role.USER.getRole();
        } 
    }
}
