package com.excilys.cdb.dto;

public class CompanyDTO {
    private Long id;
    private String name;
    private String logo;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public void setId(Long pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setLogo(String pLogo) {
        logo = pLogo;
    }

    @Override
    public String toString() {
        return name;
    }
}
