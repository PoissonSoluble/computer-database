package com.excilys.cdb.validation.exceptions;

@SuppressWarnings("serial")
public class InvalidDatesException extends ValidationException {
    public InvalidDatesException() {
        super("The dates are not in a valid order.");
    }
}
