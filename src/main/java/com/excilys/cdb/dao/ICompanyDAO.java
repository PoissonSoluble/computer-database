package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;

public interface ICompanyDAO {
    public void deleteCompany(Long id) throws DAOException;

    public Optional<Company> getCompany(Company company) throws DAOException;

    public Optional<Company> getCompany(Long id) throws DAOException;
    
    public int getCompanyListPageTotalAmount(int pageSize) throws DAOException;
    
    public List<Company> listCompanies() throws DAOException;
    
    public List<Company> listCompaniesByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException, DAOException;
}
