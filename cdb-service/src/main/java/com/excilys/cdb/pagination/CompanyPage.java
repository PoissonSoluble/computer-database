package com.excilys.cdb.pagination;

import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ICompanyService;

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
        return companyService.getPage(0, pageSize, "").getTotalPages();
    }

    @Override
    protected void refresh() {
        elements = companyService.getPage(pageNumber - 1, pageSize, search).getContent();
    }

}
