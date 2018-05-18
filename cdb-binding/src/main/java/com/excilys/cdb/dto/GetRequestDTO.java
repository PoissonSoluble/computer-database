package com.excilys.cdb.dto;

import org.springframework.data.domain.Sort.Direction;

public class GetRequestDTO {
    private int pageNumber = -1;
    private int pageSize = -1;
    private String search = "";
    private ComputerOrdering order = ComputerOrdering.CU_ID;
    private Direction direction = Direction.ASC;

    public GetRequestDTO() {
    }

    public GetRequestDTO(int pPageNumber, int pPageSize, String pSearch, ComputerOrdering pOrder,
            Direction pDirection) {
        pageNumber = pPageNumber;
        pageSize = pPageSize;
        search = pSearch;
        order = pOrder;
        direction = pDirection;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String pSearch) {
        search = pSearch;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pPageNumber) {
        pageNumber = pPageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pPageSize) {
        pageSize = pPageSize;
    }

    public ComputerOrdering getOrder() {
        return order;
    }

    public void setOrder(ComputerOrdering pOrder) {
        order = pOrder;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction pDirection) {
        direction = pDirection;
    }

}
