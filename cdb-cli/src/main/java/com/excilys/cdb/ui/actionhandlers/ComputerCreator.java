package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.webclient.ComputerRequestHandler;

@Component("computerCreator")
public class ComputerCreator implements CLIActionHandler {

    private ComputerRequestHandler computerRequestHandler;
    private CLIUserInputsAPI cliApi;

    public ComputerCreator(ComputerRequestHandler pComputerRequestHandler, CLIUserInputsAPI pCliApi) {
        computerRequestHandler = pComputerRequestHandler;
        cliApi = pCliApi;
    }

    @Override
    public boolean handle() {
        Computer computer = cliApi.askParametersForComputer(new Computer());
        if (computer != null) {
            int status = computerRequestHandler.postComputer(computer);
            if (status < 300 && status >= 200) {
                System.out.println("Creation completed. Code : " + status);
            } else {
                System.out.println("Creation failed ! Code : " + status);
            }
        }
        return true;
    }
}
