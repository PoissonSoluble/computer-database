package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface IComputerService extends IService<Computer> {

    void createComputer(Computer computer) throws ValidationException;

    void deleteComputer(Computer computer);

    void deleteComputer(Long id);

    void deleteComputers(List<Computer> ids);

    boolean exists(Computer computer);

    Optional<Computer> getComputer(Long id);
    
    int getComputerAmount(String search);
    
    List<Computer> getComputers();

    List<Computer> getComputersBySearchWithOrder(String search, ComputerOrdering order, Direction direction);

    List<Computer> getComputersBySearch(String search);

    List<Computer> getComputersWithOrder(ComputerOrdering order, Direction direction);

    Page<Computer> getPage(int page, int pageSize, String search);

    Page<Computer> getPage(int page, int pageSize, String search, ComputerOrdering order, Direction ascending);

    void updateComputer(Computer computer) throws ValidationException, ServiceException;

}