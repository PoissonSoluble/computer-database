package com.excilys.cdb.dto;

public class ComputerDTO {
    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private String company;

    public String getCompany() {
        return company;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public long getId() {
        return id;
    }

    public String getIntroduced() {
        return introduced;
    }

    public String getName() {
        return name;
    }

    public void setCompany(String pCompany) {
        company = pCompany;
    }

    public void setDiscontinued(String pDiscontinued) {
        discontinued = pDiscontinued;
    }

    public void setId(long pId) {
        id = pId;
    }

    public void setIntroduced(String pIntroduced) {
        introduced = pIntroduced;
    }

    public void setName(String pName) {
        name = pName;
    }

}
