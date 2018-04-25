package com.excilys.cdb.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.excilys.cdb.model.Company;

public interface CompanyDAO extends CrudRepository<Company, Long> {
    Page<Company> findAll(Pageable pageable);

    Page<Company> findAllByNameContaining(Pageable pageable, String name);
}
