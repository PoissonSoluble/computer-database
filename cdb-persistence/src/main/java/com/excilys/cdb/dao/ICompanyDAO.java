package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;

public interface ICompanyDAO {
    public void deleteCompany(Long id);

    public Optional<Company> getCompany(Long id);
    
    public int getCompanyListPageTotalAmount(int pageSize);
    
    public List<Company> listCompanies();
    
    public List<Company> listCompaniesByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException;
}
