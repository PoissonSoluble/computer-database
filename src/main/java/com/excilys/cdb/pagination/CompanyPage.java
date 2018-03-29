package com.excilys.cdb.pagination;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ServiceException;

public class CompanyPage extends Page<Company> {

    private static CompanyService service = CompanyService.INSTANCE;

    public CompanyPage(int pPageNumber, int pPageSize, String search) {
        super(pPageNumber, pPageSize, Optional.ofNullable(search));
        refresh();
    }

    @Override
    protected int getLastPageNumber() {
        try {
            return service.getCompanyListPageTotalAmount(pageSize);
        } catch (ServiceException e) {
            return 1;
        }
    }

    @Override
    protected void refresh() {
        try {
            elements = service.getCompanyPage(pageNumber, pageSize, search);
        } catch (ServiceException e) {
            elements = new ArrayList<>();
        }
    }

}
