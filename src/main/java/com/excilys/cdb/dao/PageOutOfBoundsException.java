package com.excilys.cdb.dao;

public class PageOutOfBoundsException extends Exception {

    private static final long serialVersionUID = -6490604657863495234L;

    public PageOutOfBoundsException() {
        super("There is not enough data in the table to fill this page.");
    }

}
