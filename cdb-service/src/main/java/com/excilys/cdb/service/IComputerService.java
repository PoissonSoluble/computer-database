package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface IComputerService {

    void createComputer(Computer computer) throws ValidationException;

    void deleteComputer(Computer computer);

    void deleteComputers(List<Computer> ids);

    boolean exists(Computer computer);

    Optional<Computer> getComputer(Long id);

    int getComputerAmount(String search);

    Page<Computer> getComputerPage(int page, int pageSize, String search) throws ServiceException;

    Page<Computer> getComputerPage(int page, int pageSize, String search, ComputerOrdering order, Direction ascending);

    void updateComputer(Computer computer) throws ValidationException;

}