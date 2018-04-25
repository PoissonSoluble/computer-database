package com.excilys.cdb.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.excilys.cdb.model.Company;

public interface ICompanyService {

    void deleteCompany(Long id);

    boolean exists(Long id);

    List<Company> getCompanies();

    Page<Company> getCompanyPage(int page, int pageSize, String search) throws ServiceException;

}