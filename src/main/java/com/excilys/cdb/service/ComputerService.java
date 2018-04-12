package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.dao.IComputerDAO;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.ComputerValidator;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Service("computerService")
public class ComputerService implements IComputerService {

    @Autowired
    private IComputerDAO dao;
    @Autowired
    private ComputerValidator validator;
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    @Override
    public void createComputer(Computer computer) throws ValidationException {
        validator.validateComputer(computer);
        try {
            dao.createComputer(computer);
            LOGGER.info(new StringBuilder("Computer creation : ").append(computer).toString());
        } catch (DAOException e) {
            LOGGER.debug("createComputer : {}", e);
        }
    }

    @Override
    public void deleteComputer(Long id) {
        try {
            dao.deleteComputer(id);
            LOGGER.info(new StringBuilder("Computer removal : ").append(id).toString());
        } catch (DAOException e) {
            LOGGER.debug("deleteComputer : {}", e);
        }
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void deleteComputers(List<Long> ids) {
        try {
            dao.deleteComputers(ids);
            LOGGER.info(new StringBuilder("Computers removal : ").append(ids).toString());
        } catch (DAOException e) {
            LOGGER.debug("deleteComputer : {}", e);
        }
    }

    @Override
    public boolean exists(Long id) {
        try {
            return dao.getComputer(id).isPresent();
        } catch (DAOException e) {
            LOGGER.debug("exists : {}", e);
            return false;
        }
    }

    @Override
    public Optional<Computer> getComputer(Long id) {
        try {
            return dao.getComputer(id);
        } catch (DAOException e) {
            LOGGER.debug("detailComputer : {}", e);
            return Optional.empty();
        }
    }

    @Override
    public int getComputerAmount(String search) throws ServiceException {
        try {
            return dao.getComputerAmount(search);
        } catch (DAOException e) {
            LOGGER.debug("getComputerAmount : {}", e);
            throw new ServiceException("Error while getting the computer count.");
        }
    }

    @Override
    public int getComputerListPageTotalAmount(int pageSize, String search) throws ServiceException {
        try {
            return dao.getComputerListPageTotalAmount(pageSize, search);
        } catch (DAOException e) {
            LOGGER.debug("getComputerListPageTotalAmount : {}", e);
            throw new ServiceException("Error while getting the total page amount.");
        }
    }

    @Override
    public List<Computer> getComputerPage(int page, int pageSize, String search) throws ServiceException {
        try {
            return dao.listComputersByPage(page, pageSize, search, ComputerOrdering.CU_ID, true);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        } catch (DAOException e) {
            LOGGER.debug("getComputerPage : {}", e);
            throw new ServiceException("Error while getting the page.");
        }
    }

    @Override
    public List<Computer> getComputerPage(int page, int pageSize, String search, ComputerOrdering order,
            boolean ascending) throws ServiceException {
        try {
            return dao.listComputersByPage(page, pageSize, search, order, ascending);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        } catch (DAOException e) {
            LOGGER.debug("getComputerPage : {}", e);
            throw new ServiceException("Error while getting the page.");
        }
    }

    @Override
    public void updateComputer(Computer computer) throws ValidationException {
        validator.validateComputer(computer);
        try {
            dao.updateComputer(computer);
            LOGGER.info(new StringBuilder("Computer update : ").append(computer).toString());
        } catch (DAOException e) {
            LOGGER.debug("updateComputer : {}", e);
        }
    }
}
