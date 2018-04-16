package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/applicationContext.xml"})
public class ComputerDAOTest {

    @Autowired
    IComputerDAO computerDAO;
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
    public void testCreation() throws DAOException {
        Computer computer = new Computer.Builder("New Computer").build();
        Optional<Long> idOpt = computerDAO.createComputer(computer);
        assertTrue(idOpt.isPresent());
        assertTrue(computerDAO.getComputer(idOpt.get()).isPresent());
    }
    
    public void testDeletion() throws DAOException {
        computerDAO.deleteComputer(5L);
        assertFalse(computerDAO.getComputer(5L).isPresent());
    }

    @Test
    public void testListComputers() throws DAOException {
        assertEquals(computerDAO.listComputers().size(), 100);
    }

    @Test
    public void testPage() throws PageOutOfBoundsException, DAOException {
        assertEquals(computerDAO.listComputersByPage(1, 10, ComputerOrdering.CU_ID, true).size(), 10);
    }

    @Test(expected = PageOutOfBoundsException.class)
    public void testPageOutOfBounds() throws PageOutOfBoundsException, DAOException {
        computerDAO.listComputersByPage(11, 10, ComputerOrdering.CU_ID, true);
    }

    @Test
    public void testComputerUpdate() throws NoSuchElementException, DAOException {
        Computer computer = new Computer.Builder(1L).withName("New Computer 1").build();
        computerDAO.updateComputer(computer);
        assertEquals(computerDAO.getComputer(1L).get().getName().get(), "New Computer 1");
    }

    @Test
    public void testGetComputer() throws NoSuchElementException, DAOException {
        Optional<Computer> computerOpt = computerDAO.getComputer(2L);
        assertTrue(computerOpt.isPresent());
        assertEquals(computerOpt.get().getId().get(), new Long(2));
        assertEquals(computerOpt.get().getName().get(), "Computer 2");
        assertEquals(computerOpt.get().getIntroduced(), Optional.empty());
        assertEquals(computerOpt.get().getDiscontinued(), Optional.empty());
        assertEquals(computerOpt.get().getCompany().get().getId().get(), new Long(2));
        assertEquals(computerOpt.get().getCompany().get().getName().get(), "Company 2");
    }

    @Test
    public void testPageAmount() throws DAOException {
        assertEquals(computerDAO.getComputerListPageTotalAmount(10), 10);
    }


    @Test
    public void testComputerAmount() throws DAOException {
        assertEquals(computerDAO.getComputerAmount(), 100);
    }
    

    @Test
    public void testComputerAmountSearch() throws DAOException {
        assertEquals(computerDAO.getComputerAmount("Computer 1"), 12);
    }
    
    @Test
    public void testMultipleDelete() throws DAOException {
        List<Long> ids = new ArrayList<>();
        ids.add(5L);
        ids.add(15L);
        ids.add(25L);
        computerDAO.deleteComputers(ids);
        for(Long id : ids) {
            assertFalse(computerDAO.getComputer(id).isPresent());
        }
    }
}
