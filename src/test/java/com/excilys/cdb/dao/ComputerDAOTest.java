package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {
    
    ComputerDAO dao = ComputerDAO.INSTANCE;

    @AfterClass
    public static void destroy() {
        MockDataBase.removeDataBase();
    }

    @BeforeClass
    public static void setUp() {
        MockDataBase.createDatabase();
    }

    @Test
    public void testCreationAndDeletion() {
        Computer computer = new Computer.Builder("New Computer").build();
        Optional<Long> idOpt = dao.createComputer(computer);
        assertTrue(idOpt.isPresent());
        assertTrue(dao.getComputer(idOpt.get()).isPresent());
        dao.deleteComputer(idOpt.get());
        assertFalse(dao.getComputer(idOpt.get()).isPresent());
    }

    @Test
    public void testListComputers() {
        assertEquals(dao.listComputers().size(), 100);
    }

    @Test
    public void testPage() throws PageOutOfBoundsException {
        assertEquals(dao.listComputersByPage(1, 10).size(), 10);
    }

    @Test(expected = PageOutOfBoundsException.class)
    public void testPageOutOfBounds() throws PageOutOfBoundsException {
        dao.listComputersByPage(11, 10);
    }

    @Test
    public void testComputerUpdate() throws NoSuchElementException {
        Computer computer = new Computer.Builder(1L).withName("New Computer 1").build();
        dao.updateComputer(computer);
        assertEquals(dao.getComputer(1L).get().getName().get(), "New Computer 1");
    }

    @Test
    public void testGetComputer() throws NoSuchElementException {
        Optional<Computer> computerOpt = dao.getComputer(2L);
        assertTrue(computerOpt.isPresent());
        assertEquals(computerOpt.get().getId().get(), new Long(2));
        assertEquals(computerOpt.get().getName().get(), "Computer 2");
        assertEquals(computerOpt.get().getIntroduced(), Optional.empty());
        assertEquals(computerOpt.get().getDiscontinued(), Optional.empty());
        assertEquals(computerOpt.get().getCompany().get().getId(), new Long(2));
        assertEquals(computerOpt.get().getCompany().get().getName(), "Company 2");
    }

    @Test
    public void testPageAmount() {
        assertEquals(dao.getComputerListPageTotalAmount(10), 10);
    }
}
