package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;

public class CompanyDAOTest {

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
        assertEquals(CompanyDAO.INSTANCE.listCompanies().size(), 20);
    }
    
    @Test
    public void testPage() {
        try {
            assertEquals(CompanyDAO.INSTANCE.listCompaniesByPage(1, 10).size(), 10);
        } catch (PageOutOfBoundsException e) {
            fail("The first page should be available.");
        }
    }
    
    @Test
    public void testPageOutOfBounds() {
        try {
            CompanyDAO.INSTANCE.listCompaniesByPage(3, 10);
            fail("This page should not exist.");
        } catch (PageOutOfBoundsException e) {
            assertTrue(true);
        }
    }  
    
    @Test
    public void testGetCompany() {
        Optional<Company> companyOpt = CompanyDAO.INSTANCE.getCompany(2L);
        assertTrue(companyOpt.isPresent());
        try {
            assertEquals(companyOpt.get().getId(), new Long(2));
            assertEquals(companyOpt.get().getName(), "Company 2");
        } catch (NoSuchElementException e) {
            fail("Wrong company.");
        }
    }
}
