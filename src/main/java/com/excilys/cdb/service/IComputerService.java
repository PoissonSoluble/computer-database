package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface IComputerService {

    void createComputer(Computer computer) throws ValidationException;

    void deleteComputer(Long id);

    void deleteComputers(List<Long> ids);

    boolean exists(Long id);

    Optional<Computer> getComputer(Long id);

    int getComputerAmount(String search) throws ServiceException;

    int getComputerListPageTotalAmount(int pageSize, String search) throws ServiceException;

    List<Computer> getComputerPage(int page, int pageSize, String search) throws ServiceException;

    List<Computer> getComputerPage(int page, int pageSize, String search, ComputerOrdering order, boolean ascending)
            throws ServiceException;

    void updateComputer(Computer computer) throws ValidationException;

}