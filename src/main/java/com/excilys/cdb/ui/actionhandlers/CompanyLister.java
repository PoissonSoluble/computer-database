package com.excilys.cdb.ui.actionhandlers;

import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.ui.CommandLineInterface;

public class CompanyLister implements CLIActionHandler{

	private CompanyService service = CompanyService.INSTANCE;
	private final int PAGE_SIZE = 20;

	@Override
	public void handle() {
		int page = 1;
		int totalPages = service.getCompanyListPageTotalAmount(PAGE_SIZE);
		printPages(page, totalPages);
	}

	private void appendCompanies(int page, StringBuilder sb) {
		service.getCompanyPage(page, PAGE_SIZE).forEach(company -> {
			sb.append(company).append("\n");
		});
	}

	private String getPage(int page, int totalPages) {
		StringBuilder sb = new StringBuilder("======== COMPANIES ========\n");
		sb.append("======== ID - NAME ========\n");
		appendCompanies(page, sb);
		sb.append("Page ").append(page).append("/").append(totalPages).append("\n");
		sb.append("(Press ENTER for next page, Q + ENTER for exit)");
		return sb.toString();
	}
	
	private void printPages(int page, int totalPages) {
		while (true) {
			System.out.println(getPage(page, totalPages));
			String input = CommandLineInterface.getUserInput();
			if (input.toLowerCase().equals("q") || page == totalPages) {
				return;
			} else{
				page++;
				continue;
			}
		}
	}
}
