package com.excilys.cdb.ui.actionhandlers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.CommandLineInterface;
import com.excilys.cdb.ui.container.Page;
import com.excilys.cdb.ui.webclient.ComputerRESTPageHandler;

@Component("computerLister")
public class ComputerLister implements CLIActionHandler {

    private ComputerRESTPageHandler computerRESTPageHandler;
    private Page<Computer> page;

    public ComputerLister(ComputerRESTPageHandler pComputerRESTPageHandler) {
        computerRESTPageHandler = pComputerRESTPageHandler;
    }

    @Override
    public boolean handle() {
        page = computerRESTPageHandler.getListFromREST(0, Page.DEFAULT_PAGE_SIZE);
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
                .orElse(PageChoice.CURRENT_PAGE).handle(page, computerRESTPageHandler));
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
