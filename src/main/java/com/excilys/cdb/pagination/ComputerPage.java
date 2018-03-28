package com.excilys.cdb.pagination;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;

public class ComputerPage extends Page<Computer> {

    private static ComputerService service = ComputerService.INSTANCE;

    public ComputerPage(int pPageNumber, int pPageSize, String search) {
        super(pPageNumber, pPageSize, Optional.ofNullable(search));
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
    protected int getLastPageNumber() {
        try {
            return service.getComputerListPageTotalAmount(pageSize, search);
        } catch (ServiceException e) {
            return 1;
        }
    }

    @Override
    protected void refresh() {
        try {
            elements = service.getComputerPage(pageNumber, pageSize, search);
        } catch (ServiceException e) {
            elements = new ArrayList<>();
        }
        pageTotal = getLastPageNumber();
    }
}
