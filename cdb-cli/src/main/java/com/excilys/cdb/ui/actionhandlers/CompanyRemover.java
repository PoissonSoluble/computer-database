package com.excilys.cdb.ui.actionhandlers;

import org.springframework.stereotype.Component;

import com.excilys.cdb.service.ICompanyService;

@Component("companyRemover")
public class CompanyRemover implements CLIActionHandler {

    private ICompanyService companyService;
    private CLIUserInputsAPI cliApi;

    public CompanyRemover(ICompanyService pCompanyService, CLIUserInputsAPI pCliApi) {
        companyService = pCompanyService;
        cliApi = pCliApi;
    }
    
    @Override
    public boolean handle() {
        try {
            Long id = cliApi.askID("company");
            if (!companyService.exists(id)) {
                System.out.println("This company does not exists.");
                return true;
            }
            companyService.deleteCompany(id);
            System.out.println("The company was successfuly deleted.\n");
        } catch (NumberFormatException e) {
            System.out.println("This is not a proper ID format. (an integer)");
        }
        return true;
    }
}
