package com.excilys.cdb.ui.actionhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.service.IComputerService;

@Component("computerRemover")
public class ComputerRemover implements CLIActionHandler {

    private IComputerService computerService;
    private CLIUserInputsAPI cliApi;

    @Autowired
    public ComputerRemover(IComputerService pComputerService, CLIUserInputsAPI pCliApi) {
        computerService = pComputerService;
        cliApi = pCliApi;
    }
    
    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("computer");
            if (!computerService.exists(id)) {
                System.out.println("This computer does not exists.");
                return true;
            }
            computerService.deleteComputer(id);
            System.out.println("The computer was successfuly deleted.\n");
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }
}
