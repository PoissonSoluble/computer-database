package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

@Component("exitHandler")
public class ExitHandler implements CLIActionHandler {

    @Override
    public boolean handle() {
        System.out.println("Goodbye !");
        return false;
    }
}
