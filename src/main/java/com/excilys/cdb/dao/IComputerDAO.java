package com.excilys.cdb.dao;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;

public interface IComputerDAO {
    public Optional<Long> createComputer(Computer computer);

    public void deleteComputer(Long id);

    public void deleteComputers(List<Long> ids);

    public Optional<Computer> getComputer(Long id);

    public int getComputerAmount();

    public int getComputerAmount(String search);

    public int getComputerListPageTotalAmount(int pageSize);

    public int getComputerListPageTotalAmount(int pageSize, String search);

    public List<Computer> listComputers();

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, ComputerOrdering order, boolean ascending)
            throws PageOutOfBoundsException;

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, String search, ComputerOrdering order,
            boolean ascending) throws PageOutOfBoundsException;

    public void updateComputer(Computer computer);
    
    public void deleteComputerFromCompany(Long companyId);
}
