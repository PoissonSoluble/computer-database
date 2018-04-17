package com.excilys.cdb.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.ICompanyMapper;
import com.excilys.cdb.model.Company;

@Repository("companyDAO")
public class CompanyDAO implements ICompanyDAO {

    private ICompanyMapper companyMapper;
    private JdbcTemplate jdbcTemplate;

    private final static Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
    private final static String SELECT_ALL = "SELECT ca_id, ca_name FROM company";
    private final static String SELECT_FROM_ID = "SELECT ca_id, ca_name FROM company WHERE ca_id = ?";
    private final static String SELECT_COUNT = "SELECT count(ca_id) as count FROM company";
    private final static String SELECT_A_PAGE = "SELECT ca_id, ca_name FROM company ORDER BY ca_id LIMIT ? OFFSET ?";
    private final static String DELETE_COMPANY = "DELETE FROM company WHERE ca_id = ?";

    public CompanyDAO(DataSource pDataSource, ICompanyMapper pCompanyMapper) {
        companyMapper = pCompanyMapper;
        jdbcTemplate = new JdbcTemplate(pDataSource);
    }

    @Override
    public void deleteCompany(Long id) {
        LOGGER.info("Company DAO : delete");
        jdbcTemplate.update(DELETE_COMPANY, new Object[] { id });
    }

    @Override
    public Optional<Company> getCompany(Long id) {
        LOGGER.info("Company DAO : get");
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_ID, new Object[] { id },
                    (resultSet, rowNum) -> retrieveCompanyFromQuery(resultSet));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int getCompanyListPageTotalAmount(int pageSize) {
        LOGGER.info("Company DAO : page number");
        return jdbcTemplate.query(SELECT_COUNT, (resultSet, rowNum) -> {
            return Integer.valueOf(computePageAmountFromQuery(pageSize, resultSet));
        }).get(0);
    }

    @Override
    public List<Company> listCompanies() {
        LOGGER.info("Company DAO : list");
        return jdbcTemplate.query(SELECT_ALL, (resultSet, rowNum) -> {
            return retrieveCompanyFromQuery(resultSet).orElse(new Company());
        });
    }

    @Override
    public List<Company> listCompaniesByPage(int pageNumber, int pageSize) throws PageOutOfBoundsException {
        LOGGER.info(new StringBuilder("Company DAO : page (").append(pageNumber).append(",").append(pageSize)
                .append(")").toString());
        List<Company> companies = jdbcTemplate.query(SELECT_A_PAGE, preparedStatement -> {
            retrieveParametersForCompanyPage(pageNumber, pageSize, preparedStatement);
        }, (resultSet, rowNum) -> {
            return retrieveCompanyFromQuery(resultSet).orElse(new Company());
        });

        if (companies.isEmpty()) {
            throw new PageOutOfBoundsException();
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

    private Optional<Company> retrieveCompanyFromQuery(ResultSet rs) throws SQLException {
        return Optional.ofNullable(companyMapper.createCompany(rs.getLong(1), rs.getString(2)));
    }

    private void retrieveParametersForCompanyPage(int pageNumber, int pageSize, PreparedStatement stmt)
            throws SQLException {
        stmt.setInt(1, pageSize);
        stmt.setInt(2, pageSize * (pageNumber - 1));
    }

}
