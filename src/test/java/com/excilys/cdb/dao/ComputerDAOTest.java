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

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {

    ComputerDAO dao = ComputerDAO.INSTANCE;

    @After
    public void destroy() {
        MockDataBase.removeDataBase();
    }

    @Before
    public void setUp() {
        MockDataBase.createDatabase();
    }

    @Test
    public void testCreation() throws DAOException {
        Computer computer = new Computer.Builder("New Computer").build();
        Optional<Long> idOpt = dao.createComputer(computer);
        assertTrue(idOpt.isPresent());
        assertTrue(dao.getComputer(idOpt.get()).isPresent());
    }
    
    public void testDeletion() throws DAOException {
        dao.deleteComputer(5L);
        assertFalse(dao.getComputer(5L).isPresent());
    }

    @Test
    public void testListComputers() throws DAOException {
        assertEquals(dao.listComputers().size(), 100);
    }

    @Test
    public void testPage() throws PageOutOfBoundsException, DAOException {
        assertEquals(dao.listComputersByPage(1, 10).size(), 10);
    }

    @Test(expected = PageOutOfBoundsException.class)
    public void testPageOutOfBounds() throws PageOutOfBoundsException, DAOException {
        dao.listComputersByPage(11, 10);
    }

    @Test
    public void testComputerUpdate() throws NoSuchElementException, DAOException {
        Computer computer = new Computer.Builder(1L).withName("New Computer 1").build();
        dao.updateComputer(computer);
        assertEquals(dao.getComputer(1L).get().getName().get(), "New Computer 1");
    }

    @Test
    public void testGetComputer() throws NoSuchElementException, DAOException {
        Optional<Computer> computerOpt = dao.getComputer(2L);
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
        assertEquals(dao.getComputerListPageTotalAmount(10), 10);
    }

    @Test
    public void testMultipleDelete() throws DAOException {
        List<Long> ids = new ArrayList<>();
        ids.add(5L);
        ids.add(15L);
        ids.add(25L);
        dao.deleteComputers(ids);
        for(Long id : ids) {
            assertFalse(dao.getComputer(id).isPresent());
        }
    }
}
