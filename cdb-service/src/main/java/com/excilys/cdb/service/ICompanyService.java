package com.excilys.cdb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface ICompanyService extends IService<Company> {

    void deleteCompany(Long id);

    boolean exists(Long id);

    List<Company> getCompanies();

    Page<Company> getPage(int page, int pageSize, String search);

    void createCompany(Company company) throws ValidationException;

}