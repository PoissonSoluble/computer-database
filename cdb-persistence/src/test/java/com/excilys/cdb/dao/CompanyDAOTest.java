package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.cdb.config.DataSourceConfig;
import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataSourceConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("cli")
public class CompanyDAOTest {
    @Autowired
    private ICompanyDAO companyDAO;
    @Autowired
    private IComputerDAO computerDAO;
    @Autowired
    private MockDataBase mockDataBase;   
    
    @After
    public void destroy() {
        mockDataBase.removeDataBase();
    }

    @Before
    public void setUp() {
        mockDataBase.createDatabase();
    }

    @Test
    public void testListCompanies() {
        assertEquals(companyDAO.listCompanies().size(), 20);
    }

    @Test
    public void testPage() throws PageOutOfBoundsException {
        assertEquals(companyDAO.listCompaniesByPage(1, 10).size(), 10);
    }

    @Test(expected = PageOutOfBoundsException.class)
    public void testPageOutOfBounds() throws PageOutOfBoundsException {
        companyDAO.listCompaniesByPage(3, 10);
    }

    @Test
    public void testGetCompany() throws NoSuchElementException {
        Optional<Company> companyOpt = companyDAO.getCompany(2L);
        assertTrue(companyOpt.isPresent());
        assertEquals(companyOpt.get().getId().get(), new Long(2));
        assertEquals(companyOpt.get().getName().get(), "Company 2");
    }

    @Test
    public void testDelete() throws NoSuchElementException {
        computerDAO.deleteComputerFromCompany(2L);
        companyDAO.deleteCompany(2L);
        assertFalse(companyDAO.getCompany(2L).isPresent());
        assertFalse(computerDAO.getComputer(2L).isPresent());
    }
}
