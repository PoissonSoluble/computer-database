package com.excilys.cdb.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public interface ComputerDAO extends CrudRepository<Computer, Long> {
    Long deleteByCompany(Company Company);
    
    List<Computer> findAll(Sort sort);

    Page<Computer> findAll(Pageable pageable);

    List<Computer> findAllByNameContaining(String name);

    List<Computer> findAllByNameContaining(String name, Sort sort);
    
    Page<Computer> findAllByNameContaining(Pageable pageable, String name);

    List<Computer> findAllByCompany_IdAndNameContaining(Long id, String name);
    
    List<Computer> findAllByCompany_IdAndNameContaining(Long id, String name, Sort sort);
    
    Page<Computer> findAllByCompany_IdAndNameContaining(Pageable pageable, Long id, String name);
    
    int countByNameContaining(String name);
}
