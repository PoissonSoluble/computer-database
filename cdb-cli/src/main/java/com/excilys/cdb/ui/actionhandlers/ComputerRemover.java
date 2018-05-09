package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.ui.webclient.ComputerRequestHandler;

@Component("computerRemover")
public class ComputerRemover implements CLIActionHandler {

    private ComputerRequestHandler computerRequestHandler;
    private CLIUserInputsAPI cliApi;

    public ComputerRemover(ComputerRequestHandler pComputerRequestHandler, CLIUserInputsAPI pCliApi) {
        computerRequestHandler = pComputerRequestHandler;
        cliApi = pCliApi;
    }
    
    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("computer");
            int status = computerRequestHandler.deleteComputer(id);

            if (status < 300 && status >= 200) {
                System.out.println("Removal completed. Code : " + status);
            } else {
                System.out.println("Removal failed ! Code : " + status);
            }
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }
}
