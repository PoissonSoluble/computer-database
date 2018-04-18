package com.excilys.cdb.validation.exceptions;

@SuppressWarnings("serial")
public class NotExistingCompanyException extends ValidationException {
    public NotExistingCompanyException() {
        super("This company does not exists.");
    }
}
