package com.excilys.cdb.ui;

import java.util.Arrays;
import java.util.List;

import com.excilys.cdb.ui.actionhandlers.CLIActionHandler;
import com.excilys.cdb.ui.actionhandlers.CompanyLister;
import com.excilys.cdb.ui.actionhandlers.ComputerCreator;
import com.excilys.cdb.ui.actionhandlers.ComputerItemizer;
import com.excilys.cdb.ui.actionhandlers.ComputerLister;
import com.excilys.cdb.ui.actionhandlers.ComputerModifier;
import com.excilys.cdb.ui.actionhandlers.ComputerRemover;

enum UserChoice {
    LIST_COMPUTERS("1/ List computers", new ComputerLister(), "1", "list computers"),
    LIST_COMPANIES("2/ List companies", new CompanyLister(), "2", "list companies"),
    DETAIL_COMPUTER("3/ Detail computer", new ComputerItemizer(), "3", "detail computer"),
    CREATE_COMPUTER("4/ Create computer", new ComputerCreator(), "4", "create computer"),
    UPDATE_COMPUTER("5/ Update computer", new ComputerModifier(), "5", "update computer"),
    REMOVE_COMPUTER("6/ Remove computer", new ComputerRemover(), "6", "remove computer"),
    EXIT("7/ Exit", null, "7", "exit");

    private List<String> validChoices;
    private String title;
    private CLIActionHandler handler;

    UserChoice(String pTitle, CLIActionHandler pHandler, String... pValidChoices) {
        title = pTitle;
        handler = pHandler;
        validChoices = Arrays.asList(pValidChoices);
    }

    public boolean accept(String choice) {
        if (validChoices.contains(choice.toLowerCase())) {
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public boolean handleChoice() {
        if (handler == null) {
            return false;
        } else {
            handler.handle();
            return true;
        }
    }
}
