package com.excilys.cdb.ui.actionhandlers;

import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.ui.CommandLineInterface;

public class ComputerLister implements CLIActionHandler {

	private ComputerService service = ComputerService.INSTANCE;
	private final int PAGE_SIZE = 20;

	@Override
	public void handle() {
		int page = 1;
		int totalPages = service.getComputerListPageTotalAmount(PAGE_SIZE);
		printPages(page, totalPages);
	}

	private void appendComputers(int page, StringBuilder sb) {
		service.getComputerPage(page, PAGE_SIZE).forEach(computer -> {
			sb.append(computer).append("\n");
		});
	}

	private String getPage(int page, int totalPages) {
		StringBuilder sb = new StringBuilder("======== COMPUTERS ========\n");
		sb.append("=== ID - NAME (COMPANY) ===\n");
		appendComputers(page, sb);
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
