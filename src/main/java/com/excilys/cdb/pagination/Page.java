package com.excilys.cdb.pagination;

import java.util.List;
import java.util.Optional;

public abstract class Page<T> {
    protected List<T> elements;
    protected int pageNumber;
    protected int pageSize;
    protected int pageTotal;
    protected String search;

    public Page(int pPageNumber, int pPageSize, Optional<String> pSearch) {
        pageNumber = pPageNumber;
        pageSize = pPageSize;
        search = pSearch.orElse("");
        pageTotal = getLastPageNumber();
    }

    public List<T> first() {
        pageNumber = 1;
        refresh();
        return elements;
    }

    public List<T> get() {
        return elements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public boolean isLastPage() {
        return pageNumber == pageTotal;
    }

    public List<T> last() {
        pageNumber = pageTotal;
        refresh();
        return elements;
    }

    public List<T> next() {
        if (pageNumber < pageTotal) {
            pageNumber++;
        }
        refresh();
        return elements;
    }

    public List<T> previous() {
        if (pageNumber > 1) {
            pageNumber--;
        }
        refresh();
        return elements;
    }

    protected abstract int getLastPageNumber();

    protected abstract void refresh();

}
