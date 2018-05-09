package com.excilys.cdb.ui.actionhandlers;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.ui.CommandLineInterface;
import com.excilys.cdb.ui.container.Page;
import com.excilys.cdb.ui.webclient.CompanyRESTPageHandler;

@Component("companyLister")
public class CompanyLister implements CLIActionHandler {

    private CompanyRESTPageHandler companyRESTPageHandler;
    private Page<Company> page;

    public CompanyLister(CompanyRESTPageHandler pCompanyRESTPageHandler) {
        companyRESTPageHandler = pCompanyRESTPageHandler;
    }


    @Override
    public boolean handle() {
        page = companyRESTPageHandler.getListFromREST(0, Page.DEFAULT_PAGE_SIZE);
        printPages();
        return true;
    }

    private String getPageString(Page<Company> companies) {
        StringBuilder sb = new StringBuilder("======== COMPANIES ========\n");
        sb.append("======== ID - NAME ========\n");
        companies.getContent().forEach(company -> {
            sb.append(company).append("\n");
        });
        sb.append("Page ").append(companies.getNumber() + 1).append("/").append(page.getTotalPages()).append("\n");
        return sb.toString();
    }

    private Optional<Page<Company>> handleChoice() {
        String input = CommandLineInterface.getUserInput().toLowerCase();
        @SuppressWarnings("unchecked")
        Page<Company> handle = (Page<Company>) (Stream.of(PageChoice.values()).filter(v -> v.accept(input)).findFirst()
                .orElse(PageChoice.CURRENT_PAGE).handle(page, companyRESTPageHandler));
        return Optional.ofNullable(handle);
    }

    private void printPageMenu() {
        StringBuilder menu = new StringBuilder();
        Stream.of(PageChoice.values()).forEach(value -> menu.append(value.getTitle()).append(", "));
        menu.append("please pick one.");
        System.out.println(menu);
    }

    private void printPages() {
        while (true) {
            System.out.println(getPageString(page));
            printPageMenu();
            Optional<Page<Company>> pageOpt = handleChoice();
            if (pageOpt.isPresent()) {
                page = pageOpt.get();
            } else {
                return;
            }
        }
    }
}
