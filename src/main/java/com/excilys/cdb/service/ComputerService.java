package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.DAOException;
import com.excilys.cdb.dao.PageOutOfBoundsException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.ComputerValidator;
import com.excilys.cdb.validation.exceptions.ValidationException;

public enum ComputerService {
    INSTANCE;

    private ComputerDAO dao = ComputerDAO.INSTANCE;
    private ComputerValidator validator = ComputerValidator.INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    public void createComputer(Computer computer) throws ValidationException {
        validator.validateComputer(computer);
        try {
            dao.createComputer(computer);
            LOGGER.info(new StringBuilder("Computer creation : ").append(computer).toString());
        } catch (DAOException e) {
            LOGGER.debug("createComputer : {}", e);
        }
    }

    public void deleteComputer(Long id) {
        try {
            dao.deleteComputer(id);
            LOGGER.info(new StringBuilder("Computer removal : ").append(id).toString());
        } catch (DAOException e) {
            LOGGER.debug("deleteComputer : {}", e);
        }
    }
    

    public void deleteComputers(List<Long> ids) {
        try {
            dao.deleteComputers(ids);
            LOGGER.info(new StringBuilder("Computers removal : ").append(ids).toString());
        } catch (DAOException e) {
            LOGGER.debug("deleteComputer : {}", e);
        }
    }

    public Optional<Computer> getComputer(Long id) {
        try {
            return dao.getComputer(id);
        } catch (DAOException e) {
            LOGGER.debug("detailComputer : {}", e);
            return Optional.empty();
        }
    }

    public boolean exists(Long id) {
        try {
            return dao.getComputer(id).isPresent();
        } catch (DAOException e) {
            LOGGER.debug("exists : {}", e);
            return false;
        }
    }

    public int getComputerAmount() throws ServiceException {
        try {
            return dao.getComputerAmount();
        } catch (DAOException e) {
            LOGGER.debug("getComputerAmount : {}", e);
            throw new ServiceException("Error while getting the computer count.");
        }
    }

    public int getComputerListPageTotalAmount(int pageSize) throws ServiceException {
        try {
            return dao.getComputerListPageTotalAmount(pageSize);
        } catch (DAOException e) {
            LOGGER.debug("getComputerListPageTotalAmount : {}", e);
            throw new ServiceException("Error while getting the total page amount.");
        }
    }

    public List<Computer> getComputerPage(int page, int pageSize) throws ServiceException{
        try {
            return dao.listComputersByPage(page, pageSize);
        } catch (PageOutOfBoundsException e) {
            return new ArrayList<>();
        } catch (DAOException e) {
            LOGGER.debug("getComputerPage : {}", e);
            throw new ServiceException("Error while getting the page.");
        }
    }

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
