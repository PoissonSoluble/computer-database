package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Company;

@Service("companyService")
public class CompanyService implements ICompanyService {

    private CompanyDAO companyDAO;
    private ComputerDAO computerDAO;
    private final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

    public CompanyService(CompanyDAO pCompanyDAO, ComputerDAO pComputerDAO) {
        companyDAO = pCompanyDAO;
        computerDAO = pComputerDAO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCompany(Long id) {
        computerDAO.deleteByCompany(new Company.Builder(id).build());
        companyDAO.deleteById(id);
        LOGGER.info(new StringBuilder("Company removal : ").append(id).toString());
    }

    @Override
    public boolean exists(Long id) {
        return companyDAO.findById(id).isPresent();
    }

    @Override
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<>();
        companyDAO.findAll().forEach(companies::add);
        return companies;
    }

    @Override
    public Page<Company> getCompanyPage(int page, int pageSize, String search) {
        return companyDAO.findAllByNameContaining(PageRequest.of(page, pageSize, Sort.by("id")), search);
    }

}
