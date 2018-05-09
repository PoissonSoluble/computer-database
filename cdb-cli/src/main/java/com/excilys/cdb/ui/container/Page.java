package com.excilys.cdb.ui.container;

import java.util.List;

public class Page<T> {
    public static final int DEFAULT_PAGE_SIZE = 20;
    
    private List<T> content;
    private int number;
    private int totalPages;

    public Page(List<T> pContent, int pNumber, int pTotalPages) {
        content = pContent;
        number = pNumber;
        totalPages = pTotalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public int getNumber() {
        return number;
    }
    
    public int getSize() {
        return content.size();
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTotalPages(int totalPage) {
        this.totalPages = totalPage;
    }

}
