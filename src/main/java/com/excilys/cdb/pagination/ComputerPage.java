package com.excilys.cdb.pagination;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;

public class ComputerPage extends Page<Computer> {

    private IComputerService computerService;
    private ComputerOrdering order;
    private boolean ascending;

    public ComputerPage(int pPageNumber, int pPageSize, String search, ComputerOrdering pOrder, boolean pAscending,
            IComputerService pService) {
        super(pPageNumber, pPageSize, Optional.ofNullable(search));
        order = pOrder;
        ascending = pAscending;
        computerService = pService;
        pageTotal = getLastPageNumber();
        refresh();
        
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("======== COMPUTERS ========\n");
        sb.append("=== ID - NAME (COMPANY) ===\n");
        elements.forEach(computer -> sb.append(computer).append("\n"));
        sb.append("Page ").append(pageNumber).append("/").append(pageTotal).append("\n");
        sb.append("(Press ENTER for next page, Q + ENTER for exit)");
        return sb.toString();
    }

    @Override
    protected int getLastPageNumber() {
        try {
            return computerService.getComputerListPageTotalAmount(pageSize, search);
        } catch (ServiceException e) {
            return 1;
        }
    }

    @Override
    protected void refresh() {
        try {
            elements = computerService.getComputerPage(pageNumber, pageSize, search, order, ascending);
        } catch (ServiceException e) {
            elements = new ArrayList<>();
        }
        pageTotal = getLastPageNumber();
    }
}
