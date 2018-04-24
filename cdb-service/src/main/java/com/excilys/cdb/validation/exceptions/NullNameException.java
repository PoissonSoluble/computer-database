package com.excilys.cdb.validation.exceptions;

@SuppressWarnings("serial")
public class NullNameException extends ValidationException {
    public NullNameException() {
        super("The name cannot be null.");
    }
}
