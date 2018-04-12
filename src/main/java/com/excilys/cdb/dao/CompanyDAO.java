package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

@Repository("companyDAO")
public class CompanyDAO implements ICompanyDAO{

    @Autowired
    private DataSource dataSource;
    private CompanyMapper mapper = CompanyMapper.INSTANCE;
    @Autowired
    private IComputerDAO computerDAO;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
    private final static String SELECT_ALL = "SELECT ca_id, ca_name FROM company";
    private final static String SELECT_FROM_ID = "SELECT ca_id, ca_name FROM company WHERE ca_id = ?";
    private final static String SELECT_COUNT = "SELECT count(ca_id) as count FROM company";
    private final static String SELECT_A_PAGE = "SELECT ca_id, ca_name FROM company ORDER BY ca_id LIMIT ? OFFSET ?";
    private final static String DELETE_COMPANY = "DELETE FROM company WHERE ca_id = ?";

    public void deleteCompany(Long id) throws DAOException {
        LOGGER.info("Company DAO : delete");
        try (Connection conn = getConnection();) {
            conn.setAutoCommit(false);
            executeDeleteStatement(id, conn);
            conn.commit();
        } catch (SQLException e) {
            LOGGER.debug(new StringBuilder("deleteCompany(): ").append(e.getMessage()).toString());
            throw new DAOException("Error while deleting the company.");
        }
    }

    public Optional<Company> getCompany(Company company) throws DAOException {
        return getCompany(company.getId().orElseThrow(() -> new DAOException("The id is null.")));
    }

    public Optional<Company> getCompany(Long id) throws DAOException {
        LOGGER.info("Company DAO : get");
        Company company = null;
        try (Connection conn =getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_FROM_ID);) {
            stmt.setLong(1, id);
            company = retrieveCompanyFromQuery(stmt);
        } catch (SQLException e) {
            LOGGER.debug(new StringBuilder("getCompany(): ").append(e.getMessage()).toString());
            throw new DAOException("Error while getting the company.");
        }
        return Optional.ofNullable(company);
    }

    public int getCompanyListPageTotalAmount(int pageSize) throws DAOException {
        LOGGER.info("Company DAO : page number");
        int pages = 0;
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_COUNT);
                ResultSet rs = stmt.executeQuery();) {
            rs.next();
            pages = computePageAmountFromQuery(pageSize, rs);
        } catch (SQLException e) {
            LOGGER.debug(new StringBuilder("getCompanyListPageTotalAmount(int): ").append(e.getMessage()).toString());
            throw new DAOException("Error while getting total page count.");
        }
        return pages;
    }

    public List<Company> listCompanies() throws DAOException {
        LOGGER.info("Company DAO : list");
        ArrayList<Company> companies = new ArrayList<>();
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(SELECT_ALL);
                ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                companies.add(mapper.createCompany(rs.getLong(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            LOGGER.debug(new StringBuilder("listCompanies(): ").append(e.getMessage()).toString());
            throw new DAOException("Error while listing companies.");
        }
        return companies;
    }

    public List<Company> listCompaniesByPage(int pageNumber, int pageSize)
            throws PageOutOfBoundsException, DAOException {
        LOGGER.info(new StringBuilder("Company DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
        ArrayList<Company> companies = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(SELECT_A_PAGE);) {
            retrieveParametersForComputerPage(pageNumber, pageSize, stmt);
            retrievePageContentFromQueryResult(stmt, companies);
        } catch (SQLException e) {
            LOGGER.debug(new StringBuilder("listCompaniesByPage(): ").append(e.getMessage()).toString());
            throw new DAOException("Error while retriving page.");
        }
        return companies;
    }

    private int computePageAmountFromQuery(int pageSize, ResultSet rs) throws SQLException {
        int pages;
        int count;
        count = rs.getInt("count");
        pages = count / pageSize;
        pages += (count % pageSize) != 0 ? 1 : 0;
        return pages;
    }

    private void executeDeleteStatement(Long id, Connection conn) throws SQLException, DAOException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_COMPANY);) {
            computerDAO.deleteComputerFromCompany(id, conn);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException | DAOException e) {
            conn.rollback();
            LOGGER.error("deleteCompany : {}");
            throw new DAOException("Error while deleting the company.");
        }
    }

    private Company retrieveCompanyFromQuery(PreparedStatement stmt) throws SQLException {
        try (ResultSet rs = stmt.executeQuery();) {
            if (rs.next()) {
                return mapper.createCompany(rs.getLong(1), rs.getString(2));
            }
        }
        return null;
    }

    private void retrievePageContentFromQueryResult(PreparedStatement stmt, ArrayList<Company> companies)
            throws SQLException, PageOutOfBoundsException {
        try (ResultSet rs = stmt.executeQuery();) {
            while (rs.next()) {
                companies.add(mapper.createCompany(rs.getLong(1), rs.getString(2)));
            }
            if (companies.isEmpty()) {
                throw new PageOutOfBoundsException();
            }
        }
    }

    private void retrieveParametersForComputerPage(int pageNumber, int pageSize, PreparedStatement stmt)
            throws SQLException {
        stmt.setInt(1, pageSize);
        stmt.setInt(2, pageSize * (pageNumber - 1));
    }
    
    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
