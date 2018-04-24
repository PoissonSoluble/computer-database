package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

@Component("defaultHandler")
public class DefaultHandler  implements CLIActionHandler {

    @Override
    public boolean handle() {
        System.err.println("This choice is not valid.\n");
        return true;
    }
}
