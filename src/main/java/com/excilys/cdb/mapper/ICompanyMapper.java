package com.excilys.cdb.mapper;

import com.excilys.cdb.model.Company;

public interface ICompanyMapper {

    Company createCompany(Long id);

    Company createCompany(Long id, String name);

    Company createCompany(String name);

}