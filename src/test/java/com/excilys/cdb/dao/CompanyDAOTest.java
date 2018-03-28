package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;

public class CompanyDAOTest {
    CompanyDAO dao = CompanyDAO.INSTANCE;

    @After
    public void destroy() {
        MockDataBase.removeDataBase();
    }

    @Before
    public void setUp() {
        MockDataBase.createDatabase();
    }

    @Test
    public void testListCompanies() throws DAOException {
        assertEquals(dao.listCompanies().size(), 20);
    }

    @Test
    public void testPage() throws PageOutOfBoundsException, DAOException {
        assertEquals(dao.listCompaniesByPage(1, 10).size(), 10);
    }

    @Test(expected = PageOutOfBoundsException.class)
    public void testPageOutOfBounds() throws PageOutOfBoundsException, DAOException {
        dao.listCompaniesByPage(3, 10);
    }

    @Test
    public void testGetCompany() throws NoSuchElementException, DAOException{
        Optional<Company> companyOpt = dao.getCompany(2L);
        assertTrue(companyOpt.isPresent());
        assertEquals(companyOpt.get().getId().get(), new Long(2));
        assertEquals(companyOpt.get().getName().get(), "Company 2");
    }
}
