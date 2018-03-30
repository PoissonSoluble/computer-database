package com.excilys.cdb.ui.actionhandlers;

import com.excilys.cdb.service.ComputerService;

public class ComputerRemover implements CLIActionHandler {

    private ComputerService service = ComputerService.INSTANCE;
    private CLIUserInputsAPI cliApi = CLIUserInputsAPI.INSTANCE;

    @Override
    public void handle() {
        try {
            Long id = cliApi.askID("computer");
            if (!service.exists(id)) {
                System.out.println("This computer does not exists.");
                return;
            }
            service.deleteComputer(id);
            System.out.println("The computer was successfuly deleted.\n");
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
    }
}
