package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {

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
        Optional<Long> idOpt = ComputerDAO.INSTANCE.createComputer(computer);
        assertTrue(idOpt.isPresent());
        assertTrue(ComputerDAO.INSTANCE.getComputer(idOpt.get()).isPresent());
        ComputerDAO.INSTANCE.deleteComputer(idOpt.get());
        assertFalse(ComputerDAO.INSTANCE.getComputer(idOpt.get()).isPresent());
    }

    @Test
    public void testListComputers() {
        assertEquals(ComputerDAO.INSTANCE.listComputers().size(), 100);
    }

    @Test
    public void testPage() {
        try {
            assertEquals(ComputerDAO.INSTANCE.listComputersByPage(1, 10).size(), 10);
        } catch (PageOutOfBoundsException e) {
            fail("The first page should be available.");
        }
    }

    @Test
    public void testPageOutOfBounds() {
        try {
            ComputerDAO.INSTANCE.listComputersByPage(11, 10);
            fail("This page should not exist.");
        } catch (PageOutOfBoundsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testComputerUpdate() {
        Computer computer = new Computer.Builder(1L).withName("New Computer 1").build();
        ComputerDAO.INSTANCE.updateComputer(computer);
        try {
            assertEquals(ComputerDAO.INSTANCE.getComputer(1L).get().getName().get(), "New Computer 1");
        } catch (NoSuchElementException e) {
            fail("Computer not found.");
        }
    }
    
    @Test
    public void testGetComputer() {
        Optional<Computer> computerOpt = ComputerDAO.INSTANCE.getComputer(2L);
        assertTrue(computerOpt.isPresent());
        try {
            assertEquals(computerOpt.get().getId().get(), new Long(2));
            assertEquals(computerOpt.get().getName().get(), "Computer 2");
            assertEquals(computerOpt.get().getIntroduced(), Optional.empty());
            assertEquals(computerOpt.get().getDiscontinued(), Optional.empty());
            assertEquals(computerOpt.get().getCompany().get().getId(), new Long(2));
            assertEquals(computerOpt.get().getCompany().get().getName(), "Company 2");
        } catch (NoSuchElementException e) {
            fail("Wrong computer.");
        }
    }
    
    @Test
    public void testPageAmount() {
        assertEquals(ComputerDAO.INSTANCE.getComputerListPageTotalAmount(10), 10);
    }
}
