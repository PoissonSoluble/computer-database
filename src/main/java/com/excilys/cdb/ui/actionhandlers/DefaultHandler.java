package com.excilys.cdb.ui.actionhandlers;


public class DefaultHandler  implements CLIActionHandler {

    @Override
    public boolean handle() {
        System.err.println("This choice is not valid.\n");
        return true;
    }
}
