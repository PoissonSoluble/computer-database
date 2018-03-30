package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Company;

public enum CompanyService {
    INSTANCE;

    private CompanyDAO dao = CompanyDAO.INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    public void deleteCompany(Long id) {
        try {
            dao.deleteCompany(id);
            LOGGER.info(new StringBuilder("Company removal : ").append(id).toString());
        } catch (DAOException e) {
            LOGGER.debug("deleteCompany : {}", e);
        }
    }

    public boolean exists(Long id) {
        try {
            return dao.getCompany(id).isPresent();
        } catch (DAOException e) {
            LOGGER.debug("exists : {}", e);
            return false;
        }
    }

    public List<Company> getCompanies() throws ServiceException {
        try {
            return dao.listCompanies();
        } catch (DAOException e) {
            LOGGER.debug("getCompanies : {}", e);
            throw new ServiceException("Error while retrieving the companies.");
        }
    }

    public int getCompanyListPageTotalAmount(int pageSize) throws ServiceException {
        try {
            return dao.getCompanyListPageTotalAmount(pageSize);
        } catch (DAOException e) {
            LOGGER.debug("getCompanyListPageTotalAmount : {}", e);
            throw new ServiceException("Error while retrieving total page number.");
        }
    }

    public List<Company> getCompanyPage(int page, int pageSize, String search) throws ServiceException {
        try {
            /* TODO : implement search */
            return dao.listCompaniesByPage(page, pageSize);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        } catch (DAOException e) {
            LOGGER.debug("getCompanyPage : {}", e);
            throw new ServiceException("Error while retrieving page.");
        }
    }
}
