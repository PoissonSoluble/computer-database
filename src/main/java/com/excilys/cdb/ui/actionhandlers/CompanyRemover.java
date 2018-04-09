package com.excilys.cdb.ui.actionhandlers;

import com.excilys.cdb.service.CompanyService;

public class CompanyRemover implements CLIActionHandler {

    private CompanyService service = CompanyService.INSTANCE;
    private CLIUserInputsAPI cliApi = CLIUserInputsAPI.INSTANCE;

    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("company");
            if (!service.exists(id)) {
                System.out.println("This company does not exists.");
                return true;
            }
            service.deleteCompany(id);
            System.out.println("The company was successfuly deleted.\n");
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }
}
