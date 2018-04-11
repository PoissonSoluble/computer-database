package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;

public interface IComputerDAO {
    public Optional<Long> createComputer(Computer computer) throws DAOException;

    public void deleteComputer(Long id) throws DAOException;

    public void deleteComputers(List<Long> ids) throws DAOException;

    public Optional<Computer> getComputer(Long id) throws DAOException;

    public int getComputerAmount() throws DAOException;

    public int getComputerAmount(String search) throws DAOException;

    public int getComputerListPageTotalAmount(int pageSize) throws DAOException;

    public int getComputerListPageTotalAmount(int pageSize, String search) throws DAOException;

    public List<Computer> listComputers() throws DAOException;

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, ComputerOrdering order, boolean ascending)
            throws PageOutOfBoundsException, DAOException;

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, String search, ComputerOrdering order,
            boolean ascending) throws PageOutOfBoundsException, DAOException;

    public void updateComputer(Computer computer) throws DAOException;
    
    public void deleteComputerFromCompany(Long companyId, Connection conn) throws SQLException, DAOException;
}
