package com.excilys.cdb.pagination;

import java.util.List;

public abstract class Page<T> {
    protected List<T> elements;
    protected int pageNumber;
    protected int pageSize;
    protected int pageTotal;

    public Page(int pPageNumber, int pPageSize) {
        pageNumber = pPageNumber;
        pageSize = pPageSize;
        pageTotal = getLastPageNumber();
        refresh();
    }

    public List<T> first() {
        pageNumber = 1;
        refresh();
        return elements;
    }

    public List<T> get(){
        return elements;
    }
    
    public List<T> last() {
        pageNumber = pageTotal;
        refresh();
        return elements;
    }

    public List<T> next(){
        if(pageNumber < pageTotal) {
            pageNumber++;
        }
        refresh();
        return elements;
    }

    public List<T> previous(){
        if(pageNumber > 1) {
            pageNumber--;
        }
        refresh();
        return elements;
    }
    
    public boolean isLastPage() {
        return pageNumber == pageTotal;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public int getPageTotal() {
        return pageTotal;
    }

    protected abstract int getLastPageNumber();

    protected abstract void refresh();

}
