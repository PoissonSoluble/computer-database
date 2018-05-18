package com.excilys.cdb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.excilys.cdb.dto.ComputerOrdering;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface ICompanyService extends IService<Company> {

    void deleteCompany(Long id);

    boolean exists(Long id);

    List<Company> getCompanies();

    Page<Company> getPage(int page, int pageSize, String search);

    void createCompany(Company company) throws ValidationException;

    void updateCompany(Company company) throws ValidationException, ServiceException;

    List<Company> getCompaniesBySearchWithOrder(String name, ComputerOrdering order, Direction direction);

    int getCompanyCount(String search);

}