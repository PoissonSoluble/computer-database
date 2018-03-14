package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Company;

public enum CompanyService {
	INSTANCE;

	private CompanyDAO dao = CompanyDAO.INSTANCE;
	
	public List<Company> getCompanyPage(int page, int pageSize) {
		try {
			return dao.listCompaniesByPage(page, pageSize);
		} catch (PageOutOfBoundsException e) {
			return null;
		}
	}
	
	public int getPageAmount(int pageSize) {
		return dao.getPageAmount(pageSize);
	}
}
