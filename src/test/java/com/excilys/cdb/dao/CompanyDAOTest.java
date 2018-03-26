package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;

public class CompanyDAOTest {
    CompanyDAO dao = CompanyDAO.INSTANCE;

    @AfterClass
    public static void destroy() {
        MockDataBase.removeDataBase();
    }

    @BeforeClass
    public static void setUp() {
        MockDataBase.createDatabase();
    }

    @Test
    public void testListCompanies() {
        assertEquals(dao.listCompanies().size(), 20);
    }

    @Test
    public void testPage() throws PageOutOfBoundsException {
        assertEquals(dao.listCompaniesByPage(1, 10).size(), 10);
    }

    @Test(expected = PageOutOfBoundsException.class)
    public void testPageOutOfBounds() throws PageOutOfBoundsException {
        dao.listCompaniesByPage(3, 10);
    }

    @Test
    public void testGetCompany() throws NoSuchElementException{
        Optional<Company> companyOpt = dao.getCompany(2L);
        assertTrue(companyOpt.isPresent());
        assertEquals(companyOpt.get().getId(), new Long(2));
        assertEquals(companyOpt.get().getName(), "Company 2");
    }
}
