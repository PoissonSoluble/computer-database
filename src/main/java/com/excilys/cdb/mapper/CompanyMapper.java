package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component("companyMapper")
public class CompanyMapper implements ICompanyMapper {

    @Override
    public Company createCompany(Long id) {
        if (id == null) {
            return null;
        }
        return new Company.Builder(id).build();
    }

    @Override
    public Company createCompany(Long id, String name) {
        if ((id == null) && (name == null)) {
            return null;
        }
        return new Company.Builder(id).withName(name).build();
    }

    @Override
    public Company createCompany(String name) {
        if (name == null) {
            return null;
        }
        return new Company.Builder(name).build();
    }
}
