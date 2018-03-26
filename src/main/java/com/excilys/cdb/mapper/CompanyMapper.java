package com.excilys.cdb.mapper;

import com.excilys.cdb.model.Company;

public enum CompanyMapper {

    INSTANCE;

    public Company createCompany(Long id) {
        if (id == null) {
            return null;
        }
        return new Company.Builder(id).build();
    }

    public Company createCompany(Long id, String name) {
        if ((id == null) && (name == null)) {
            return null;
        }
        return new Company.Builder(id).withName(name).build();
    }

    public Company createCompany(String name) {
        if (name == null) {
            return null;
        }
        return new Company.Builder(name).build();
    }
}
