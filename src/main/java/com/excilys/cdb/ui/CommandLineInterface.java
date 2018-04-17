package com.excilys.cdb.ui;

import java.util.Scanner;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.config.AppConfig;
import com.excilys.cdb.ui.actionhandlers.CompanyLister;
import com.excilys.cdb.ui.actionhandlers.CompanyRemover;
import com.excilys.cdb.ui.actionhandlers.ComputerCreator;
import com.excilys.cdb.ui.actionhandlers.ComputerItemizer;
import com.excilys.cdb.ui.actionhandlers.ComputerLister;
import com.excilys.cdb.ui.actionhandlers.ComputerModifier;
import com.excilys.cdb.ui.actionhandlers.ComputerRemover;
import com.excilys.cdb.ui.actionhandlers.DefaultHandler;
import com.excilys.cdb.ui.actionhandlers.ExitHandler;

@Controller("commandLineInterface")
public class CommandLineInterface {

    private static Scanner sc;

    private ComputerLister computerLister;
    private CompanyLister companyLister;
    private ComputerCreator computerCreator;
    private ComputerItemizer computerItemizer;
    private ComputerModifier computerModifier;
    private ComputerRemover computerRemover;
    private CompanyRemover companyRemover;
    private DefaultHandler defautlHandler;
    private ExitHandler exitHandler;

    public CommandLineInterface(ComputerLister pComputerLister, CompanyLister pCompanyLister,
            ComputerCreator pComputerCreator, ComputerItemizer pComputerItemizer, ComputerModifier pComputerModifier,
            ComputerRemover pComputerRemover, CompanyRemover pCompanyRemover, DefaultHandler pDefaultHandler,
            ExitHandler pExitHandler) {
        computerLister = pComputerLister;
        companyLister = pCompanyLister;
        computerCreator = pComputerCreator;
        computerItemizer = pComputerItemizer;
        computerModifier = pComputerModifier;
        computerRemover = pComputerRemover;
        companyRemover = pCompanyRemover;
        defautlHandler = pDefaultHandler;
        exitHandler = pExitHandler;
    }

    public static String getUserInput() {
        return sc.nextLine();
    }

    public static void main(String[] args) {
        @SuppressWarnings("resource")
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.getBean(CommandLineInterface.class).start();
    }

    public String getMenu() {
        StringBuilder sb = new StringBuilder("===== COMPUTER DATABASE =====\n");
        sb.append("--------- MAIN MENU ---------\n");
        Stream.of(UserChoice.values()).forEach(value -> sb.append(value.getTitle()).append("\n"));
        sb.append("\nChoose your action: ");
        return sb.toString();
    }

    public boolean handleChoice(String userInput) {
        return Stream.of(UserChoice.values()).filter(v -> v.accept(userInput)).findFirst().orElse(UserChoice.ANY)
                .handleChoice();

    }

    public void setHandlers() {
        UserChoice.LIST_COMPUTERS.setHandler(computerLister);
        UserChoice.LIST_COMPANIES.setHandler(companyLister);
        UserChoice.CREATE_COMPUTER.setHandler(computerCreator);
        UserChoice.DETAIL_COMPUTER.setHandler(computerItemizer);
        UserChoice.UPDATE_COMPUTER.setHandler(computerModifier);
        UserChoice.REMOVE_COMPUTER.setHandler(computerRemover);
        UserChoice.REMOVE_COMPANY.setHandler(companyRemover);
        UserChoice.EXIT.setHandler(exitHandler);
        UserChoice.ANY.setHandler(defautlHandler);
    }

    public void start() {
        setHandlers();
        CommandLineInterface.sc = new Scanner(System.in);
        do {
            System.out.print(getMenu());
        } while (handleChoice(getUserInput()));
        CommandLineInterface.sc.close();
    }
}
