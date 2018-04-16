package com.excilys.cdb.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.IComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Repository("computerDAO")
public class ComputerDAO implements IComputerDAO {

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
    private IComputerMapper computerMapper;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ComputerDAO(DataSource pDataSource, IComputerMapper pComputerMapper) {
        computerMapper = pComputerMapper;
        jdbcTemplate = new JdbcTemplate(pDataSource);
    }

    @Override
    public Optional<Long> createComputer(Computer computer){
        LOGGER.info("Computer DAO : creation");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            setParameters(computer, prepareStatement);
            return prepareStatement;
        }, keyHolder);
        return Optional.ofNullable(keyHolder.getKey().longValue());
    }

    @Override
    public void deleteComputer(Long id){
        LOGGER.info("Computer DAO : deletion");
        jdbcTemplate.update(DELETE, new Object[] { id });
    }

    @Override
    public void deleteComputerFromCompany(Long companyId){
        jdbcTemplate.update(DELETE_COMPANY, new Object[] { companyId });
    }

    @Override
    public void deleteComputers(List<Long> ids){
        LOGGER.info("Computer DAO : deletion (multiple)");
        jdbcTemplate.batchUpdate(DELETE, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return ids.size();
            }

            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ids.get(i));
            }
        });
    }

    @Override
    public Optional<Computer> getComputer(Long id){
        LOGGER.info("Computer DAO : get");
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_ID, new Object[] { id },
                    (resultSet, rowNum) -> Optional.of(retrieveComputerFromQuery(resultSet)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int getComputerAmount() {
        LOGGER.info("Computer DAO : count");
        return jdbcTemplate.query(SELECT_COUNT, (resultSet, rowNum) -> resultSet.getInt("count")).get(0);
    }

    @Override
    public int getComputerAmount(String search){
        if (StringUtils.isBlank(search)) {
            return getComputerAmount();
        }
        LOGGER.info("Computer DAO : count");
        return jdbcTemplate.queryForObject(SELECT_COUNT_SEARCH,
                new Object[] { new StringBuilder("%").append(search).append("%").toString() },
                (resultSet, rowNum) -> resultSet.getInt("count"));

    }

    @Override
    public int getComputerListPageTotalAmount(int pageSize){
        LOGGER.info("Computer DAO : page number");
        return jdbcTemplate.query(SELECT_COUNT, (resultSet, rowNum) -> {
            return Integer.valueOf(computePageAmountFromQuery(pageSize, resultSet));
        }).get(0);
    }

    @Override
    public int getComputerListPageTotalAmount(int pageSize, String search){
        if (StringUtils.isBlank(search)) {
            return getComputerListPageTotalAmount(pageSize);
        }
        LOGGER.info("Computer DAO : page number");
        return (Integer) jdbcTemplate.queryForObject(SELECT_COUNT_SEARCH,
                new Object[] { new StringBuilder("%").append(search).append("%").toString() }, (resultSet, rowNum) -> {
                    return Integer.valueOf(computePageAmountFromQuery(pageSize, resultSet));
                });
    }

    @Override
    public List<Computer> listComputers(){
        LOGGER.info("Computer DAO : list");
        return jdbcTemplate.query(SELECT_ALL, (resultSet, rowNum) -> {
            return retrieveComputerFromQuery(resultSet);
        });
    }

    @Override
    public List<Computer> listComputersByPage(int pageNumber, int pageSize, ComputerOrdering order, boolean ascending)
            throws PageOutOfBoundsException {
        LOGGER.info(new StringBuilder("Computer DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
        List<Computer> computers = jdbcTemplate.query(constructPageRequest(order, ascending, SELECT_PAGE),
                preparedStatement -> {
                    preparedStatement.setInt(1, pageSize);
                    preparedStatement.setInt(2, pageSize * (pageNumber - 1));
                }, (resultSet, rowNum) -> {
                    return retrieveComputerFromQuery(resultSet);
                });

        if (computers.isEmpty()) {
            throw new PageOutOfBoundsException();
        }
        return computers;
    }

    @Override
    public List<Computer> listComputersByPage(int pageNumber, int pageSize, String search, ComputerOrdering order,
            boolean ascending) throws PageOutOfBoundsException {
        if (StringUtils.isBlank(search)) {
            return listComputersByPage(pageNumber, pageSize, order, ascending);
        }
        LOGGER.info(new StringBuilder("Computer DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());

        List<Computer> computers = jdbcTemplate.query(constructPageRequest(order, ascending, SELECT_PAGE_SEARCH),
                preparedStatement -> {
                    preparedStatement.setString(1, new StringBuilder("%").append(search).append("%").toString());
                    preparedStatement.setInt(1, pageSize);
                    preparedStatement.setInt(2, pageSize * (pageNumber - 1));
                }, (resultSet, rowNum) -> {
                    return retrieveComputerFromQuery(resultSet);
                });

        if (computers.isEmpty()) {
            throw new PageOutOfBoundsException();
        }
        return computers;
    }

    @Override
    public void updateComputer(Computer computer) {
        LOGGER.info("Computer DAO : update");
        jdbcTemplate.update(UPDATE, preparedStatement -> {
            setParameters(computer, preparedStatement);
            preparedStatement.setLong(5, computer.getId().orElseThrow(() -> new SQLException()));
        });
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

    private Computer retrieveComputerFromQuery(ResultSet rs) throws SQLException {
        return computerMapper.createComputer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                rs.getLong(5), rs.getString(6));
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
}
