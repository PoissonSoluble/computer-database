package com.excilys.cdb.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.excilys.cdb.model.Company;

public interface CompanyDAO extends CrudRepository<Company, Long> {
    Page<Company> findAll(Pageable pageable);

    List<Company> findAllByNameContaining(String name);

    List<Company> findAllByNameContaining(String name, Sort sort);
    
    Page<Company> findAllByNameContaining(Pageable pageable, String name);

    int countByNameContaining(String name);
}
