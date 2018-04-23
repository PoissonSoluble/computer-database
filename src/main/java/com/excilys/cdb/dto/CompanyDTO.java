package com.excilys.cdb.dto;

public class CompanyDTO {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    @Override
    public String toString() {
        return name;
    }
}
