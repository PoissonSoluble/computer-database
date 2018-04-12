package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.DAOException;
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
    @Transactional(rollbackFor=Exception.class)
    public void deleteCompany(Long id) {
        try {
            companyDAO.deleteCompany(id);
            LOGGER.info(new StringBuilder("Company removal : ").append(id).toString());
        } catch (DAOException e) {
            LOGGER.debug("deleteCompany : {}", e);
        }
    }

    @Override
    public boolean exists(Long id) {
        try {
            return companyDAO.getCompany(id).isPresent();
        } catch (DAOException e) {
            LOGGER.debug("exists : {}", e);
            return false;
        }
    }

    @Override
    public List<Company> getCompanies() throws ServiceException {
        try {
            return companyDAO.listCompanies();
        } catch (DAOException e) {
            LOGGER.debug("getCompanies : {}", e);
            throw new ServiceException("Error while retrieving the companies.");
        }
    }

    @Override
    public int getCompanyListPageTotalAmount(int pageSize) throws ServiceException {
        try {
            return companyDAO.getCompanyListPageTotalAmount(pageSize);
        } catch (DAOException e) {
            LOGGER.debug("getCompanyListPageTotalAmount : {}", e);
            throw new ServiceException("Error while retrieving total page number.");
        }
    }

    @Override
    public List<Company> getCompanyPage(int page, int pageSize, String search) throws ServiceException {
        try {
            /* TODO : implement search */
            return companyDAO.listCompaniesByPage(page, pageSize);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        } catch (DAOException e) {
            LOGGER.debug("getCompanyPage : {}", e);
            throw new ServiceException("Error while retrieving page.");
        }
    }
}
