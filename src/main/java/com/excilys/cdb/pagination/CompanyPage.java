package com.excilys.cdb.pagination;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

public class CompanyPage extends Page<Company> {

    private static CompanyService service = CompanyService.INSTANCE;

    public CompanyPage(int pPageNumber, int pPageSize) {
        super(pPageNumber, pPageSize);
    }

    @Override
    protected int getLastPageNumber() {
        return service.getCompanyListPageTotalAmount(pageSize);
    }

    @Override
    protected void refresh() {
        elements = service.getCompanyPage(pageNumber, pageSize);
    }

}
