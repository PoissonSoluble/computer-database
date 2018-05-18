package com.excilys.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dto.ComputerOrdering;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.IComputerValidator;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Service("computerService")
public class ComputerService implements IComputerService {

    private ComputerDAO computerDAO;
    private IComputerValidator computerValidator;
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    public ComputerService(ComputerDAO pComputerDAO, IComputerValidator pComputerValidator) {
        computerDAO = pComputerDAO;
        computerValidator = pComputerValidator;
    }

    @Override
    public void createComputer(Computer computer) throws ValidationException {
        if (computer.getId().isPresent()) {
            computer.setId(null);
        }
        computerValidator.validateComputer(computer);
        computerDAO.save(computer);
        LOGGER.info(new StringBuilder("Computer creation : ").append(computer).toString());
    }

    @Override
    public void deleteComputer(Computer computer) {
        computerDAO.delete(computer);
        LOGGER.info(new StringBuilder("Computer removal : ").append(computer.getId().orElse(-1L)).toString());
    }

    @Override
    public void deleteComputer(Long id) {
        Computer computer = new Computer.Builder(id).build();
        computerDAO.delete(computer);
        LOGGER.info(new StringBuilder("Computer removal : ").append(computer.getId().orElse(-1L)).toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComputers(List<Computer> computers) {
        computerDAO.deleteAll(computers);
        LOGGER.info(new StringBuilder("Computers removal : ").append(computers).toString());
    }

    @Override
    public boolean exists(Computer computer) {
        return computerDAO.findById(computer.getId().orElse(-1L)).isPresent();
    }

    @Override
    public Optional<Computer> getComputer(Long id) {
        return computerDAO.findById(id);
    }

    @Override
    public List<Computer> getComputers() {
        List<Computer> computers = new ArrayList<>();
        computerDAO.findAll().forEach(computers::add);
        return computers;
    }

    @Override
    public List<Computer> getComputersFromCompany(Long id) {
        List<Computer> computers = new ArrayList<>();
        computerDAO.findAllByCompany_IdAndNameContaining(id, "").forEach(computers::add);
        return computers;
    }

    @Override
    public List<Computer> getComputersFromCompanyBySearchWithOrder(Long id, String search, ComputerOrdering order,
            Direction direction) {
        List<Computer> computers = computerDAO.findAllByCompany_IdAndNameContaining(id, search,
                Sort.by(direction, order.getValue()));
        return computers;
    }

    @Override
    public Page<Computer> getPageFromCompany(Long id, int page, int pageSize, String search, ComputerOrdering order,
            Direction direction) {
        return computerDAO.findAllByCompany_IdAndNameContaining(
                PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "id")), id, search);
    }

    @Override
    public List<Computer> getComputersBySearchWithOrder(String search, ComputerOrdering order, Direction direction) {
        List<Computer> computers = computerDAO.findAllByNameContaining(search, Sort.by(direction, order.getValue()));
        return computers;
    }

    @Override
    public int getComputerCount(String search) {
        return computerDAO.countByNameContaining(search);
    }

    @Override
    public Page<Computer> getPage(int page, int pageSize, String search) {
        return computerDAO.findAllByNameContaining(PageRequest.of(page, pageSize, Sort.by(Direction.ASC, "cu_id")),
                search);
    }

    @Override
    public Page<Computer> getPage(int page, int pageSize, String search, ComputerOrdering order, Direction ascending) {
        return computerDAO.findAllByNameContaining(PageRequest.of(page, pageSize, Sort.by(ascending, order.getValue())),
                search);
    }

    @Override
    public void updateComputer(Computer computer) throws ValidationException, ServiceException {
        computerDAO.findById(computer.getId().orElseThrow(() -> new ServiceException("This computer does not exist.")));
        computerValidator.validateComputer(computer);
        computerDAO.save(computer);
        LOGGER.info(new StringBuilder("Computer update : ").append(computer).toString());

    }
}
