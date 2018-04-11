package com.excilys.cdb.pagination;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.ServiceException;

public class CompanyPage extends Page<Company> {

    private ICompanyService companyService;

    public CompanyPage(int pPageNumber, int pPageSize, String search, ICompanyService pService) {
        super(pPageNumber, pPageSize, Optional.ofNullable(search));
        companyService = pService;
        pageTotal = getLastPageNumber();
        refresh();
    }

    @Override
    protected int getLastPageNumber() {
        try {
            return companyService.getCompanyListPageTotalAmount(pageSize);
        } catch (ServiceException e) {
            return 1;
        }
    }

    @Override
    protected void refresh() {
        try {
            elements = companyService.getCompanyPage(pageNumber, pageSize, search);
        } catch (ServiceException e) {
            elements = new ArrayList<>();
        }
    }

}
