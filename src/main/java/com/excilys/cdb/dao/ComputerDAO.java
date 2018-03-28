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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.DatabaseConnection;

public enum ComputerDAO {

    INSTANCE;

    private DatabaseConnection dbConn = DatabaseConnection.INSTANCE;
    private ComputerMapper mapper = ComputerMapper.INSTANCE;
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

    private final String SELECT_ALL = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id)";
    private final String SELECT_FROM_ID = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id) WHERE cu_id = ?";
    private final String SELECT_PAGE = "SELECT cu_id, cu_name, cu_introduced, cu_discontinued, ca_id, ca_name FROM computer LEFT JOIN company USING(ca_id) WHERE cu_name LIKE ? ORDER BY cu_id LIMIT ? OFFSET ?";
    private final String SELECT_COUNT_SEARCH = "SELECT count(cu_id) as count FROM computer WHERE cu_name LIKE ?";
    private final String INSERT = "INSERT INTO computer (cu_name, cu_introduced, cu_discontinued, ca_id) VALUES(?,?,?,?)";
    private final String DELETE = "DELETE FROM computer WHERE cu_id = ?";
    private final String UPDATE = "UPDATE computer SET cu_name = ?, cu_introduced = ?, cu_discontinued = ?, ca_id = ? WHERE cu_id = ?";

    public Optional<Long> createComputer(Computer computer) throws DAOException {
        LOGGER.info("Computer DAO : creation");
        try (Connection conn = dbConn.getConnection();
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
        try (Connection conn = dbConn.getConnection(); PreparedStatement stmt = conn.prepareStatement(DELETE);) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("deleteComputer(): {}", e);
            throw new DAOException("Error while removing the computer.");
        }
    }

    public void deleteComputers(List<Long> ids) throws DAOException {
        LOGGER.info("Computer DAO : deletion (multiple)");
        try (Connection conn = dbConn.getConnection();) {
            conn.setAutoCommit(false);
            applyDeletionOnList(ids, conn);
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error("deleteComputers(): {}", e);
            throw new DAOException("Error while initiating the connection.");
        }
    }

    public Optional<Computer> getComputer(Long id) throws DAOException {
        LOGGER.info("Computer DAO : get");
        Computer computer = null;
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_FROM_ID);) {
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

    public int getComputerAmount(String search) throws DAOException {
        LOGGER.info("Computer DAO : count");
        int count = 0;
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_COUNT_SEARCH);) {
            count = prepareAndRetrieveComputerCount(stmt, search);
        } catch (SQLException e) {
            LOGGER.error("getComputerListPageTotalAmount(int): {}", e);
            throw new DAOException("Error while getting the number of computers.");
        }
        return count;
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

    public int getComputerListPageTotalAmount(int pageSize) throws DAOException {
        return getComputerListPageTotalAmount(pageSize, "");
    }

    public int getComputerListPageTotalAmount(int pageSize, String search) throws DAOException {
        LOGGER.info("Computer DAO : page number");
        int pages = 0;
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_COUNT_SEARCH);) {
            pages = prepareAndExecutedPageNumberQuery(pageSize, stmt, search);
        } catch (SQLException e) {
            LOGGER.error("getComputerListPageTotalAmount(int): {}", e);
            throw new DAOException("Error while getting total page number.");
        }
        return pages;
    }

    public List<Computer> listComputers() throws DAOException {
        LOGGER.info("Computer DAO : list");
        ArrayList<Computer> computers = new ArrayList<>();
        try (Connection conn = dbConn.getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
                ResultSet rs = stmt.executeQuery();) {
            retrieveComputersFromQuery(computers, rs);
        } catch (SQLException e) {
            LOGGER.error("listComputers(): {}", e);
            throw new DAOException("Error while listing the computers.");
        }
        return computers;
    }

    public List<Computer> listComputersByPage(int pageNumber, int pageSize)
            throws PageOutOfBoundsException, DAOException {
        return listComputersByPage(pageNumber, pageSize, "%");
    }

    public List<Computer> listComputersByPage(int pageNumber, int pageSize, String search)
            throws PageOutOfBoundsException, DAOException {
        LOGGER.info(new StringBuilder("Computer DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
        ArrayList<Computer> computers = new ArrayList<>();
        try (Connection conn = dbConn.getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_PAGE);) {
            retrieveParametersForComputerPage(pageNumber, pageSize, search, stmt);
            retrievePageContentFromQueryResult(stmt, computers);
        } catch (SQLException e) {
            LOGGER.error("listComputersByPage(int,int): {}", e);
            throw new DAOException("Error while returning the page.");
        }
        return computers;
    }

    public void updateComputer(Computer computer) throws DAOException {
        LOGGER.info("Computer DAO : update");
        try (Connection conn = dbConn.getConnection(); PreparedStatement stmt = conn.prepareStatement(UPDATE);) {
            setParameters(computer, stmt);
            stmt.setLong(5, computer.getId().get());
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
                conn.rollback();
                LOGGER.error("deleteComputers(): {}", e);
                throw new DAOException("Error while deleting a row.");
            }
        }
    }

    private int computePageAmountFromQuery(int pageSize, ResultSet rs) throws SQLException {
        int pages, count;
        count = rs.getInt("count");
        pages = count / pageSize;
        pages += (count % pageSize) != 0 ? 1 : 0;
        return pages;
    }

    private int prepareAndExecutedPageNumberQuery(int pageSize, PreparedStatement stmt, String search)
            throws SQLException {
        int pages;
        stmt.setString(1, new StringBuilder("%").append(search).append("%").toString());
        try (ResultSet rs = stmt.executeQuery();) {
            rs.next();
            pages = computePageAmountFromQuery(pageSize, rs);
        }
        return pages;
    }

    private Computer retrieveComputerFromQuery(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery();) {
            if (rs.next()) {
                return mapper.createComputer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
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

    private void retrieveComputersFromQuery(ArrayList<Computer> computers, ResultSet rs) throws SQLException {
        while (rs.next()) {
            computers.add(mapper.createComputer(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4),
                    rs.getLong(5), rs.getString(6)));
        }
    }

    private void retrievePageContentFromQueryResult(PreparedStatement stmt, ArrayList<Computer> computers)
            throws SQLException, PageOutOfBoundsException {
        try (ResultSet rs = stmt.executeQuery();) {
            retrieveComputersFromQuery(computers, rs);
            if (computers.isEmpty()) {
                throw new PageOutOfBoundsException();
            }
        }
    }

    private void retrieveParametersForComputerPage(int pageNumber, int pageSize, String search, PreparedStatement stmt)
            throws SQLException {
        stmt.setString(1, new StringBuilder("%").append(search).append("%").toString());
        stmt.setInt(2, pageSize);
        stmt.setInt(3, pageSize * (pageNumber - 1));
    }

    private void setParameters(Computer computer, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, computer.getName().get());
        addDateToStatement(2, computer.getIntroduced(), stmt);
        addDateToStatement(3, computer.getDiscontinued(), stmt);
        if (computer.getCompany().isPresent() && computer.getCompany().get().getId().isPresent()) {
            stmt.setLong(4, computer.getCompany().get().getId().get());
        } else {
            stmt.setNull(4, java.sql.Types.BIGINT);
        }
    }
}
