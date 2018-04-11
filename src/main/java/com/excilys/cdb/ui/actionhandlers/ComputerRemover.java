package com.excilys.cdb.ui.actionhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.service.IComputerService;

@Component("computerRemover")
public class ComputerRemover implements CLIActionHandler {

    @Autowired
    private IComputerService service;
    @Autowired
    private CLIUserInputsAPI cliApi;

    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("computer");
            if (!service.exists(id)) {
                System.out.println("This computer does not exists.");
                return true;
            }
            service.deleteComputer(id);
            System.out.println("The computer was successfuly deleted.\n");
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }
}
