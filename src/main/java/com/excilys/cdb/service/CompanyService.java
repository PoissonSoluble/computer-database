package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Company;

public enum CompanyService {
    INSTANCE;

    private CompanyDAO dao = CompanyDAO.INSTANCE;

    public int getCompanyListPageTotalAmount(int pageSize) {
        return dao.getCompanyListPageTotalAmount(pageSize);
    }

    public List<Company> getCompanyPage(int page, int pageSize) {
        try {
            return dao.listCompaniesByPage(page, pageSize);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        }
    }
}
