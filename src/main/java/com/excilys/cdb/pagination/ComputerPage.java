package com.excilys.cdb.pagination;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class ComputerPage extends Page<Computer> {

    private ComputerService service = ComputerService.INSTANCE;

    public ComputerPage(int pPageNumber, int pPageSize) {
        super(pPageNumber, pPageSize);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("======== COMPUTERS ========\n");
        sb.append("=== ID - NAME (COMPANY) ===\n");
        elements.forEach(computer -> {
            sb.append(computer).append("\n");
        });
        sb.append("Page ").append(pageNumber).append("/").append(pageTotal).append("\n");
        sb.append("(Press ENTER for next page, Q + ENTER for exit)");
        return sb.toString();
    }

    @Override
    protected void refresh() {
        elements = service.getComputerPage(pageNumber, pageSize);
        pageTotal = getLastPageNumber();
    }

    @Override
    protected int getLastPageNumber() {
        return service.getComputerListPageTotalAmount(pageSize);
    }
}
