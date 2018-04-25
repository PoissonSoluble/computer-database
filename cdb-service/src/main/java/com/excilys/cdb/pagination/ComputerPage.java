package com.excilys.cdb.pagination;

import java.util.Optional;

import org.springframework.data.domain.Sort.Direction;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;

public class ComputerPage extends Page<Computer> {

    private IComputerService computerService;
    private ComputerOrdering order;
    private Direction direction;

    public ComputerPage(int pPageNumber, int pPageSize, String search, ComputerOrdering pOrder, Direction pDirection,
            IComputerService pService) {
        super(pPageNumber, pPageSize, Optional.ofNullable(search));
        order = pOrder;
        direction = pDirection;
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
        return computerService.getPage(0, pageSize, "").getTotalPages();
    }

    @Override
    protected void refresh() {
        elements = computerService.getPage(pageNumber - 1, pageSize, search, order, direction).getContent();
        pageTotal = getLastPageNumber();
    }
}
