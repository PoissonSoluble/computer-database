package com.excilys.cdb.validation.exceptions;

@SuppressWarnings("serial")
public class InvalidLoginException extends ValidationException{
    public InvalidLoginException() {
        super("The login is invalid.");
    }
}
