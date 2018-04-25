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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.config.DataSourceConfig;
import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataSourceConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("cli")
public class CompanyDAOTest {
    @Autowired
    private CompanyDAO companyDAO;
    @Autowired
    private ComputerDAO computerDAO;
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
        assertEquals(companyDAO.findAll().spliterator().getExactSizeIfKnown(), 20);
    }

    @Test
    public void testPage() {
        assertEquals(companyDAO.findAll(PageRequest.of(0, 10, Sort.by("id"))).getNumberOfElements(), 10);
    }

    @Test
    public void testPageOutOfBounds() {
        assertEquals(companyDAO.findAll(PageRequest.of(0, 10, Sort.by("id"))).getTotalPages(), 2);
    }

    @Test
    public void testGetCompany() throws NoSuchElementException {
        Optional<Company> companyOpt = companyDAO.findById(2L);
        assertTrue(companyOpt.isPresent());
        assertEquals(companyOpt.get().getId().get(), new Long(2));
        assertEquals(companyOpt.get().getName().get(), "Company 2");
    }
    
    @Test
    public void testSearchablePage() {
        assertEquals(companyDAO.findAllByNameContaining(PageRequest.of(0, 10), "Company 3").getNumberOfElements(), 1);
    }

    @Test
    @Transactional
    public void testDelete() throws NoSuchElementException {
        computerDAO.deleteByCompany(new Company.Builder(2L).build());
        companyDAO.deleteById(2L);
        assertFalse(companyDAO.findById(2L).isPresent());
        assertFalse(computerDAO.findById(2L).isPresent());
    }
}
