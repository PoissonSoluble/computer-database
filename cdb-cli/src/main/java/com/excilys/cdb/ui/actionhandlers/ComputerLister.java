package com.excilys.cdb.ui.actionhandlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.ui.CommandLineInterface;

@Component("computerLister")
public class ComputerLister implements CLIActionHandler {

    private IComputerService computerService;
    private final int PAGE_SIZE = 20;
    private Page<Computer> page;

    public ComputerLister(IComputerService pComputerService) {
        computerService = pComputerService;
    }

    @Override
    public boolean handle() {
        page = computerService.getPage(0, PAGE_SIZE, "");
        printPages();
        return true;
    }

    private String getPageString(List<Computer> computers) {
        StringBuilder sb = new StringBuilder("======== COMPUTERS ========\n");
        sb.append("=== ID - NAME (COMPANY) ===\n");
        computers.forEach(computer -> {
            sb.append(computer).append("\n");
        });
        sb.append("Page ").append(page.getNumber() + 1).append("/").append(page.getTotalPages()).append("\n");
        return sb.toString();
    }

    private Optional<Page<Computer>> handleChoice() {
        String input = CommandLineInterface.getUserInput().toLowerCase();
        @SuppressWarnings("unchecked")
        Page<Computer> handle = (Page<Computer>)(Stream.of(PageChoice.values()).filter(v -> v.accept(input)).findFirst()
                .orElse(PageChoice.CURRENT_PAGE).handle(page, computerService));
        return Optional.ofNullable(handle);
    }

    private void printPageMenu() {
        StringBuilder menu = new StringBuilder();
        Stream.of(PageChoice.values()).filter(pageChoice -> !pageChoice.isHidden())
                .forEach(value -> menu.append(value.getTitle()).append(", "));
        menu.append("please pick one.");
        System.out.println(menu);
    }

    private void printPages() {
        while (true) {
            System.out.println(getPageString(page.getContent()));
            printPageMenu();
            Optional<Page<Computer>> pageOpt = handleChoice();
            if (pageOpt.isPresent()) {
                page = pageOpt.get();
            } else {
                return;
            }
        }
    }
}
