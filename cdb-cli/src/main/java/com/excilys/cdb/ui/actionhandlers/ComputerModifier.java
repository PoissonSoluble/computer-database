package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.webclient.ComputerRequestHandler;

@Component("computerModifier")
public class ComputerModifier implements CLIActionHandler {

    private ComputerRequestHandler computerRequestHandler;
    private CLIUserInputsAPI cliApi;

    public ComputerModifier(ComputerRequestHandler pComputerRequestHandler, CLIUserInputsAPI pCliApi) {
        computerRequestHandler = pComputerRequestHandler;
        cliApi = pCliApi;
    }

    @Override
    public boolean handle() {
        try {
            Computer computer = new Computer.Builder(cliApi.askID("computer")).build();
            computer = cliApi.askParametersForComputer(computer);
            int status = computerRequestHandler.putComputer(computer);
            if (status < 300 && status >= 200) {
                System.out.println("Creation completed. Code : " + status);
            } else {
                System.out.println("Creation failed ! Code : " + status);
            }
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }

}
