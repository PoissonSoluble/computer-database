package com.excilys.cdb.ui.actionhandlers;

public class ExitHandler implements CLIActionHandler {

    @Override
    public boolean handle() {
        System.out.println("Goodbye !");
        return false;
    }
}
