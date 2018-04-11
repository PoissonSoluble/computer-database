package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;

public interface ICompanyService {

    void deleteCompany(Long id);

    boolean exists(Long id);

    List<Company> getCompanies() throws ServiceException;

    int getCompanyListPageTotalAmount(int pageSize) throws ServiceException;

    List<Company> getCompanyPage(int page, int pageSize, String search) throws ServiceException;

}