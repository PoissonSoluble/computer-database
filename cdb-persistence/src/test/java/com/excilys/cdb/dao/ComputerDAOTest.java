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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.cdb.config.DataSourceConfig;
import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=DataSourceConfig.class, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("cli")
public class ComputerDAOTest {

    @Autowired
    ComputerDAO computerDAO;
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
    public void testCreation() {
        Computer computer = new Computer.Builder("New Computer").build();
        computer = computerDAO.save(computer);
        assertTrue(computer.getId().isPresent());
        assertTrue(computerDAO.findById(computer.getId().get()).isPresent());
    }

    @Test
    public void testDeletion() {
        computerDAO.deleteById(5L);
        assertFalse(computerDAO.findById(5L).isPresent());
    }

    @Test
    public void testListComputers() {
        assertEquals(computerDAO.findAll().spliterator().getExactSizeIfKnown(), 100);
    }

    @Test
    public void testPage() {
        assertEquals(computerDAO.findAll(PageRequest.of(1, 10)).getSize(), 10);
    }

    @Test
    public void testPageOutOfBounds() {
        assertEquals(computerDAO.findAll(PageRequest.of(1, 10)).getTotalPages(), 10);
    }

    @Test
    public void testComputerUpdate() throws NoSuchElementException {
        Computer computer = new Computer.Builder(1L).withName("New Computer 1").build();
        computerDAO.save(computer);
        assertEquals(computerDAO.findById(1L).get().getName().get(), "New Computer 1");
    }

    @Test
    public void testGetComputer() throws NoSuchElementException {
        Optional<Computer> computerOpt = computerDAO.findById(2L);
        assertTrue(computerOpt.isPresent());
        assertEquals(computerOpt.get().getId().get(), new Long(2));
        assertEquals(computerOpt.get().getName().get(), "Computer 2");
        assertEquals(computerOpt.get().getIntroduced(), Optional.empty());
        assertEquals(computerOpt.get().getDiscontinued(), Optional.empty());
        assertEquals(computerOpt.get().getCompany().get().getId().get(), new Long(2));
        assertEquals(computerOpt.get().getCompany().get().getName().get(), "Company 2");
    }

    @Test
    public void testPageAmount() {
        assertEquals(computerDAO.findAll(PageRequest.of(1, 10)).getTotalPages(), 10);
    }

    @Test
    public void testComputerAmount() {
        assertEquals(computerDAO.count(), 100);
    }

    @Test
    public void testComputerAmountSearch() {
        assertEquals(computerDAO.countByNameContaining("Computer 1"), 12);
    }

    @Test
    public void testMultipleDelete() {
        List<Computer> computers = new ArrayList<>();
        computers.add(new Computer.Builder(5L).build());
        computers.add(new Computer.Builder(15L).build());
        computers.add(new Computer.Builder(25L).build());
        computerDAO.deleteAll(computers);
        for (Computer computer : computers) {
            assertFalse(computerDAO.findById(computer.getId().get()).isPresent());
        }
    }
}
