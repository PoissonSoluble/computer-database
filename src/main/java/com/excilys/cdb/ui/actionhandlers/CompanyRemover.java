package com.excilys.cdb.ui.actionhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.service.ICompanyService;

@Component("companyRemover")
public class CompanyRemover implements CLIActionHandler {

    @Autowired
    private ICompanyService service;
    @Autowired
    private CLIUserInputsAPI cliApi;

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
