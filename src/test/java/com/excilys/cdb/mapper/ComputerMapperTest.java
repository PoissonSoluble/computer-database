package com.excilys.cdb.mapper;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/applicationContext.xml"})
public class ComputerMapperTest {
    
    @Autowired
    private IComputerMapper mapper;

    @Test
    public void testCreation() {
        Computer computer = mapper.createComputer(1L, "Computer", Date.valueOf("1969-7-21"), Date.valueOf("1995-7-21"), 1,
                "Company");
        assertEquals(computer.getId().get(), new Long(1L));
        assertEquals(computer.getName().get(), "Computer");
        assertEquals(computer.getIntroduced().get(), LocalDate.of(1969, 7, 21));
        assertEquals(computer.getDiscontinued().get(), LocalDate.of(1995, 7, 21));
        assertEquals(computer.getCompany().get().getId().get(), new Long(1L));
        assertEquals(computer.getCompany().get().getName().get(), "Company");
    }    
    
    @Test
    public void testEmptyCreation() {
        Computer computer = mapper.createComputer(1L, "Computer", null, null, 0, null);
        assertEquals(computer.getId().get(), new Long(1L));
        assertEquals(computer.getName().get(), "Computer");
        assertEquals(computer.getIntroduced(), Optional.empty());
        assertEquals(computer.getDiscontinued(), Optional.empty());
        assertEquals(computer.getCompany(), Optional.empty());
    }
}
