package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.excilys.cdb.dto.CompanyOrdering;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface ICompanyService extends IService<Company> {

    void createCompany(Company company) throws ValidationException;

    void deleteCompany(Long id);

    boolean exists(Long id);

    List<Company> getCompanies();

    List<Company> getCompaniesBySearchWithOrder(String name, CompanyOrdering order, Direction direction);

    Optional<Company> getCompany(Long id);

    int getCompanyCount(String search);

    Page<Company> getPage(int page, int pageSize, String search, CompanyOrdering order, Direction direction);

    void updateCompany(Company company) throws ValidationException, ServiceException;

}