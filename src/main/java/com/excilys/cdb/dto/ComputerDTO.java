package com.excilys.cdb.dto;

public class ComputerDTO {
    private long id;
    private String name;
    private String introduced;
    private String discontinued;
    private String company;

    public long getId() {
        return id;
    }

    public void setId(long pId) {
        id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String pIntroduced) {
        introduced = pIntroduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String pDiscontinued) {
        discontinued = pDiscontinued;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String pCompany) {
        company = pCompany;
    }

}
