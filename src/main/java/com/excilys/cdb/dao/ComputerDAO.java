package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.IComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {

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
        computerMapper = pComputerMapper;
        jdbcTemplate = new JdbcTemplate(pDataSource);
    }

    public Optional<Long> createComputer(Computer computer) throws DAOException {
        LOGGER.info("Computer DAO : creation");
        try {
            return Optional.ofNullable(Integer.toUnsignedLong(jdbcTemplate.update(INSERT, preparedStatement -> {
                setParameters(computer, preparedStatement);
            })));
        } catch (DataAccessException e) {
            LOGGER.error("createComputer(Computer): {}", e);
            e.printStackTrace();
            throw new DAOException("Error while inserting the computer.");
        }
    }

    public void deleteComputer(Long id) throws DAOException {
        LOGGER.info("Computer DAO : deletion");
        try {
            jdbcTemplate.update(DELETE, new Object[] { id });
        } catch (DataAccessException e) {
            LOGGER.error("deleteComputer(): {}", e);
            throw new DAOException("Error while removing the computer.");
        }
    }

    public void deleteComputers(List<Long> ids) throws DAOException {
        LOGGER.info("Computer DAO : deletion (multiple)");
        try {
            jdbcTemplate.batchUpdate(DELETE, new BatchPreparedStatementSetter() {
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setLong(1, ids.get(i));
                }

                public int getBatchSize() {
                    return ids.size();
                }
            });
        } catch (DataAccessException e) {
            LOGGER.error("deleteComputers(): {}", e);
            throw new DAOException("Error while deleting a row.");
        }
    }

    public Optional<Computer> getComputer(Long id) throws DAOException {
        LOGGER.info("Computer DAO : get");
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_ID, new Object[] { id },
                    (resultSet, rowNum) -> retrieveComputerFromQuery(resultSet));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        } catch (DataAccessException e) {
            LOGGER.error("getComputer(Long): {}", e);
            throw new DAOException("Error while getting the computer.");
        }
    }

    public int getComputerAmount() throws DAOException {
        LOGGER.info("Computer DAO : count");
        try {
            return jdbcTemplate.query(SELECT_COUNT, (resultSet, rowNum) -> resultSet.getInt("count")).get(0);
        } catch (DataAccessException e) {
            LOGGER.error("getComputerAmount(): {}", e);
            throw new DAOException("Error while getting computer count.");
        }
    }

    public int getComputerAmount(String search) throws DAOException {
        if (StringUtils.isBlank(search)) {
            return getComputerAmount();
        }
        LOGGER.info("Computer DAO : count");
        try {
            return jdbcTemplate.queryForObject(SELECT_COUNT_SEARCH,
                    new Object[] { new StringBuilder("%").append(search).append("%").toString() },
                    (resultSet, rowNum) -> resultSet.getInt("count"));
        } catch (DataAccessException e) {
            LOGGER.error("getComputerAmount(String): {}", e);
            throw new DAOException("Error while getting computer count whith search.");
        }
    }

    public int getComputerListPageTotalAmount(int pageSize) throws DAOException {
        LOGGER.info("Computer DAO : page number");
        try {
            return jdbcTemplate.query(SELECT_COUNT, (resultSet, rowNum) -> {
                return Integer.valueOf(computePageAmountFromQuery(pageSize, resultSet));
            }).get(0);
        } catch (DataAccessException e) {
            LOGGER.error("getComputerListPageTotalAmount(int): {}", e);
            throw new DAOException("Error while getting computer page count.");
        }
    }

    public int getComputerListPageTotalAmount(int pageSize, String search) throws DAOException {
        if (StringUtils.isBlank(search)) {
            return getComputerListPageTotalAmount(pageSize);
        }
        LOGGER.info("Computer DAO : page number");
        try {
            return (Integer) jdbcTemplate.queryForObject(SELECT_COUNT_SEARCH,
                    new Object[] { new StringBuilder("%").append(search).append("%").toString() },
                    (resultSet, rowNum) -> {
                        return Integer.valueOf(computePageAmountFromQuery(pageSize, resultSet));
                    });
        } catch (DataAccessException e) {
            LOGGER.error("getComputerListPageTotalAmount(int, String): {}", e);
            throw new DAOException("Error while getting computer page count whith search.");
        }
    }

    public List<Computer> listComputers() throws DAOException {
        LOGGER.info("Computer DAO : list");
        try {
            return (List<Computer>) jdbcTemplate.queryForObject(SELECT_ALL, (resultSet, rowNum) -> {
                return retrieveComputersFromQuery(resultSet);
            });
        } catch (DataAccessException e) {
            LOGGER.error("listComputers(): {}", e);
            throw new DAOException("Error while listing computers.");
        }
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
        } catch (DataAccessException e) {
            LOGGER.error("listComputersByPage(int, int, ComputerOrdering, boolean): {}", e);
            throw new DAOException("Error while listing computer page.");
        }
    }

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, String search, ComputerOrdering order,
            boolean ascending) throws PageOutOfBoundsException, DAOException {
        if (StringUtils.isBlank(search)) {
            return listComputersByPage(pageNumber, pageSize, order, ascending);
        }
        LOGGER.info(new StringBuilder("Computer DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
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
        } catch (DataAccessException e) {
            LOGGER.error("listComputersByPage(int, int, search, ComputerOrdering, boolean): {}", e);
            throw new DAOException("Error while listing computer page with search.");
        }
    }

    public void updateComputer(Computer computer) throws DAOException {
        LOGGER.info("Computer DAO : update");
        try {
            jdbcTemplate.update(UPDATE, preparedStatement -> {
                setParameters(computer, preparedStatement);
                preparedStatement.setLong(5, computer.getId().orElseThrow(() -> new SQLException()));
            });
        } catch (DataAccessException e) {
            LOGGER.error("updateComputer(Computer): {}", e);
            throw new DAOException("Error while updating computer.");
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

    private Optional<Computer> retrieveComputerFromQuery(ResultSet rs) throws SQLException {
        try {
            return Optional.ofNullable(computerMapper.createComputer(rs.getLong(1), rs.getString(2), rs.getDate(3),
                    rs.getDate(4), rs.getLong(5), rs.getString(6)));
        } catch (SQLException e) {
            return Optional.empty();
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
}
