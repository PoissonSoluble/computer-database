package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.ICompanyDAO;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Company;

@Service("companyService")
public class CompanyService implements ICompanyService {

    private ICompanyDAO companyDAO;
    private final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    @Autowired
    public CompanyService(ICompanyDAO pCompanyDAO) {
        companyDAO = pCompanyDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(Long id) {
        companyDAO.deleteCompany(id);
        LOGGER.info(new StringBuilder("Company removal : ").append(id).toString());

    }

    @Override
    public boolean exists(Long id) {
        return companyDAO.getCompany(id).isPresent();
    }

    @Override
    public List<Company> getCompanies() {
        return companyDAO.listCompanies();
    }

    @Override
    public int getCompanyListPageTotalAmount(int pageSize) {
        return companyDAO.getCompanyListPageTotalAmount(pageSize);
    }

    @Override
    public List<Company> getCompanyPage(int page, int pageSize, String search) {
        try {
            /* TODO : implement search */
            return companyDAO.listCompaniesByPage(page, pageSize);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        }
    }
}
