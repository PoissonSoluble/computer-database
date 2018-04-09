package com.excilys.cdb.dao;

@SuppressWarnings("serial")
public class PageOutOfBoundsException extends Exception {

    public PageOutOfBoundsException() {
        super("There is not enough data in the table to fill this page.");
    }

}
