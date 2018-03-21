package com.excilys.cdb.dto;

public class CompanyDTO {
    private long id;
    private String name;

    public CompanyDTO() {
    }

    public long getId() {
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

}
