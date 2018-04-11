package com.excilys.cdb.ui;

import java.util.Arrays;
import java.util.List;

import com.excilys.cdb.ui.actionhandlers.CLIActionHandler;

public enum UserChoice {
    LIST_COMPUTERS("1/ List computers", "1", "list computers"),
    LIST_COMPANIES("2/ List companies", "2", "list companies"),
    DETAIL_COMPUTER("3/ Detail computer", "3", "detail computer"),
    CREATE_COMPUTER("4/ Create computer", "4", "create computer"),
    UPDATE_COMPUTER("5/ Update computer", "5", "update computer"),
    REMOVE_COMPUTER("6/ Remove computer", "6", "remove computer"),
    REMOVE_COMPANY("7/ Remove company", "7", "remove company"),
    EXIT("8/ Exit", "8", "exit"),
    ANY("");

    private List<String> validChoices;
    private String title;
    private CLIActionHandler handler;

    UserChoice(String pTitle, String... pValidChoices) {
        title = pTitle;
        validChoices = Arrays.asList(pValidChoices);
    }

    public boolean accept(String choice) {
        return validChoices.contains(choice.toLowerCase());
    }

    public void setHandler(CLIActionHandler pHandler) {
        handler = pHandler;
    }

    public String getTitle() {
        return title;
    }

    public boolean handleChoice() {
        return handler.handle();
    }
}
