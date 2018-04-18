package com.excilys.cdb.validation.exceptions;

@SuppressWarnings("serial")
public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException() {
        super();
    }
}
