package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.ui.webclient.CompanyRequestHandler;

@Component("companyRemover")
public class CompanyRemover implements CLIActionHandler {

    private CompanyRequestHandler companyRequestHandler;
    private CLIUserInputsAPI cliApi;

    public CompanyRemover(CompanyRequestHandler pCompanyRequestHandler, CLIUserInputsAPI pCliApi) {
        companyRequestHandler = pCompanyRequestHandler;
        cliApi = pCliApi;
    }
    
    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("company");
            int status = companyRequestHandler.deleteCompany(id);

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
