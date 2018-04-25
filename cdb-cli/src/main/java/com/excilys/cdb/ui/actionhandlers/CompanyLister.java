package com.excilys.cdb.ui.actionhandlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.ui.CommandLineInterface;

@Component("companyLister")
public class CompanyLister implements CLIActionHandler {

    private ICompanyService companyService;
    private final int PAGE_SIZE = 20;
    private Page<Company> page;

    public CompanyLister(ICompanyService pCompanyService) {
        companyService = pCompanyService;
    }

    @Override
    public boolean handle() {
        page = companyService.getPage(0, PAGE_SIZE, "");
        printPages();
        return true;
    }

    private String getPageString(List<Company> companies) {
        StringBuilder sb = new StringBuilder("======== COMPANIES ========\n");
        sb.append("======== ID - NAME ========\n");
        companies.forEach(company -> {
            sb.append(company).append("\n");
        });
        sb.append("Page ").append(page.getNumber()+1).append("/").append(page.getTotalPages()).append("\n");
        return sb.toString();
    }

    private Optional<Page<Company>> handleChoice() {
        String input = CommandLineInterface.getUserInput().toLowerCase();
        @SuppressWarnings("unchecked")
        Page<Company> handle = (Page<Company>)(Stream.of(PageChoice.values()).filter(v -> v.accept(input)).findFirst()
                .orElse(PageChoice.CURRENT_PAGE).handle(page, companyService));
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
            System.out.println(getPageString(page.getContent()));
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
