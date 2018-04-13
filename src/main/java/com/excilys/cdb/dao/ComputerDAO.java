package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.IComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {

    private DataSource dataSource;
    private IComputerMapper computerMapper;
    private JdbcTemplate jdbcTemplate;
    private final static Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private final static String SELECT_ALL = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id)";
    private final static String SELECT_FROM_ID = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id) WHERE cu_id = ?";
    private final static String SELECT_PAGE = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id) ORDER BY %s  LIMIT ? OFFSET ?";
    private final static String SELECT_PAGE_SEARCH = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id) WHERE cu_name LIKE ? ORDER BY %s LIMIT ? OFFSET ?";
    private final static String SELECT_COUNT = "SELECT count(cu_id) as count FROM computer";
    private final static String SELECT_COUNT_SEARCH = "SELECT count(cu_id) as count FROM computer WHERE cu_name LIKE ?";
    private final static String INSERT = "INSERT INTO computer (cu_name, cu_introduced, cu_discontinued, ca_id) VALUES(?,?,?,?)";
    private final static String DELETE = "DELETE FROM computer WHERE cu_id = ?";
    private final static String DELETE_COMPANY = "DELETE FROM computer WHERE ca_id = ?";
    private final static String UPDATE = "UPDATE computer SET cu_name = ?, cu_introduced = ?, cu_discontinued = ?, ca_id = ? WHERE cu_id = ?";

    @Autowired
    public ComputerDAO(DataSource pDataSource, IComputerMapper pComputerMapper) {
        dataSource = pDataSource;
        computerMapper = pComputerMapper;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<Long> createComputer(Computer computer) throws DAOException {
        LOGGER.info("Computer DAO : creation");
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
            setParameters(computer, stmt);
            stmt.executeUpdate();
            retrieveComputerIDFromUpdate(computer, stmt);
        } catch (SQLException e) {
            LOGGER.error("createComputer(): {}", e);
            throw new DAOException("Error while creating the computer.");
        }
        return computer.getId();
    }

    public void deleteComputer(Long id) throws DAOException {
        LOGGER.info("Computer DAO : deletion");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE);) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("deleteComputer(): {}", e);
            throw new DAOException("Error while removing the computer.");
        }
    }

    public void deleteComputers(List<Long> ids) throws DAOException {
        LOGGER.info("Computer DAO : deletion (multiple)");
        Connection conn = getConnection();
        try {
            applyDeletionOnList(ids, conn);
        } catch (SQLException e) {
            LOGGER.error("deleteComputers(): {}", e);
            throw new DAOException("Error while initiating the connection.");
        }
    }

    public Optional<Computer> getComputer(Long id) throws DAOException {
        LOGGER.info("Computer DAO : get");
        Computer computer = null;
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_FROM_ID);) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery();) {
                computer = retrieveComputerFromQuery(stmt);
            }
        } catch (SQLException e) {
            LOGGER.error("getComputer(Long): {}", e);
            throw new DAOException("Error while getting the computer.");
        }
        return Optional.ofNullable(computer);
    }

    public int getComputerAmount() throws DAOException {
        LOGGER.info("Computer DAO : count");
        int count = 0;
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_COUNT);
                ResultSet rs = stmt.executeQuery();) {
            rs.next();
            count = rs.getInt("count");
        } catch (SQLException e) {
            LOGGER.error("getComputerListPageTotalAmount(int): {}", e);
            throw new DAOException("Error while getting the number of computers.");
        }
        return count;
    }

    public int getComputerAmount(String search) throws DAOException {
        LOGGER.info("Computer DAO : count");
        int count = 0;
        if (StringUtils.isBlank(search)) {
            return getComputerAmount();
        }
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_COUNT_SEARCH);) {
            count = prepareAndRetrieveComputerCount(stmt, search);
        } catch (SQLException e) {
            LOGGER.error("getComputerListPageTotalAmount(int): {}", e);
            throw new DAOException("Error while getting the number of computers.");
        }
        return count;
    }

    public int getComputerListPageTotalAmount(int pageSize) throws DAOException {
        return getComputerListPageTotalAmount(pageSize, "");
    }

    public int getComputerListPageTotalAmount(int pageSize, String search) throws DAOException {
        LOGGER.info("Computer DAO : page number");
        return (Integer) jdbcTemplate.queryForObject(SELECT_COUNT_SEARCH,
                new Object[] { new StringBuilder("%").append(search).append("%").toString() }, (resultSet, rowNum) -> {
                    return Integer.valueOf(computePageAmountFromQuery(pageSize, resultSet));
                });
    }

    public List<Computer> listComputers() throws DAOException {
        LOGGER.info("Computer DAO : list");
        return (List<Computer>) jdbcTemplate.queryForObject(SELECT_ALL, (resultSet, rowNum) -> {
            return retrieveComputersFromQuery(resultSet);
        });
    }

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, ComputerOrdering order, boolean ascending)
            throws PageOutOfBoundsException, DAOException {
        LOGGER.info(new StringBuilder("Computer DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
        try {
            return (List<Computer>) jdbcTemplate.queryForObject(constructPageRequest(order, ascending, SELECT_PAGE),
                    new Object[] { Integer.valueOf(pageSize), Integer.valueOf(pageSize * (pageNumber - 1)) },
                    (resultSet, rowNum) -> {
                        return retrieveComputersFromQuery(resultSet);
                    });
        } catch (EmptyResultDataAccessException e) {
            throw new PageOutOfBoundsException();
        }
    }

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, String search, ComputerOrdering order,
            boolean ascending) throws PageOutOfBoundsException, DAOException {
        LOGGER.info(new StringBuilder("Computer DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
        if (StringUtils.isBlank(search)) {
            return listComputersByPage(pageNumber, pageSize, order, ascending);
        }
        try {
            return (List<Computer>) jdbcTemplate.queryForObject(
                    constructPageRequest(order, ascending, SELECT_PAGE_SEARCH),
                    new Object[] { new StringBuilder("%").append(search).append("%").toString(),
                            Integer.valueOf(pageSize), Integer.valueOf(pageSize * (pageNumber - 1)) },
                    (resultSet, rowNum) -> {
                        return retrieveComputersFromQuery(resultSet);
                    });
        } catch (EmptyResultDataAccessException e) {
            throw new PageOutOfBoundsException();
        }
    }

    public void updateComputer(Computer computer) throws DAOException {
        LOGGER.info("Computer DAO : update");
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE);) {
            setParameters(computer, stmt);
            stmt.setLong(5, computer.getId().orElseThrow(() -> new DAOException("Id is null.")));
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("updateComputer(Computer): {}", e);
            throw new DAOException("Error while updating the computer.");
        }
    }

    private void addDateToStatement(int parameterIndex, Optional<LocalDate> date, PreparedStatement stmt)
            throws SQLException {
        if (date.isPresent()) {
            stmt.setDate(parameterIndex, Date.valueOf(date.get()));
        } else {
            stmt.setNull(parameterIndex, java.sql.Types.DATE);
        }
    }

    private void applyDeletionOnList(List<Long> ids, Connection conn) throws SQLException, DAOException {
        for (Long id : ids) {
            try (PreparedStatement stmt = conn.prepareStatement(DELETE)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            } catch (SQLException e) {
                LOGGER.error("deleteComputers(): {}", e);
                throw new DAOException("Error while deleting a row.");
            }
        }
    }

    private int computePageAmountFromQuery(int pageSize, ResultSet rs) throws SQLException {
        int pages;
        int count;
        count = rs.getInt("count");
        pages = count / pageSize;
        pages += (count % pageSize) != 0 ? 1 : 0;
        return pages;
    }

    private String constructPageRequest(ComputerOrdering order, boolean ascending, String originalRequest) {
        String request = order.toString();
        if (!ascending) {
            request += " DESC";
        }
        return String.format(originalRequest, request);

    }

    private int prepareAndRetrieveComputerCount(PreparedStatement stmt, String search) throws SQLException {
        int count;
        stmt.setString(1, new StringBuilder("%").append(search).append("%").toString());
        try (ResultSet rs = stmt.executeQuery();) {
            rs.next();
            count = rs.getInt("count");
        }
        return count;
    }

    private Computer retrieveComputerFromQuery(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery();) {
            if (rs.next()) {
                return computerMapper.createComputer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                        rs.getLong(5), rs.getString(6));
            }
        }
        return null;
    }

    private void retrieveComputerIDFromUpdate(Computer computer, PreparedStatement stmt) throws SQLException {
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                computer.setId(generatedKeys.getLong(1));
            } else {
                computer.setId(null);
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    private List<Computer> retrieveComputersFromQuery(ResultSet rs) throws SQLException {

        List<Computer> computers = new ArrayList<>();
        do {
            computers.add(computerMapper.createComputer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                    rs.getLong(5), rs.getString(6)));
        } while (rs.next());
        return computers;
    }

    private void setParameters(Computer computer, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, computer.getName().orElse(""));
        addDateToStatement(2, computer.getIntroduced(), stmt);
        addDateToStatement(3, computer.getDiscontinued(), stmt);
        Optional<Company> companyOpt = computer.getCompany();
        if (companyOpt.isPresent() && companyOpt.get().getId().isPresent()) {
            stmt.setLong(4, companyOpt.get().getId().get());
        } else {
            stmt.setNull(4, java.sql.Types.BIGINT);
        }
    }

    public void deleteComputerFromCompany(Long companyId, Connection conn) throws SQLException, DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_COMPANY);) {
            stmt.setLong(1, companyId);
            stmt.executeUpdate();
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
}
