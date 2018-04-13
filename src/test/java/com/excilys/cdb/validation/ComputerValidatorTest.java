package com.excilys.cdb.validation;

import java.time.LocalDate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"/applicationContext.xml"})
public class ComputerValidatorTest {
    
    @Autowired
    private MockDataBase mockDataBase;

    @Autowired
    private IComputerValidator validator;

    public ComputerValidatorTest() {
    }

    @After
    public void destroy() {
        mockDataBase.removeDataBase();
    }
    
    @Before
    public void setUp() {
        mockDataBase.createDatabase();
    }

    @Test(expected = NotExistingCompanyException.class)
    public void testCompanyInvalidation() throws ValidationException {
        Computer computer = new Computer.Builder("Computer 1").withCompany(new Company.Builder(50L).build()).build();
        validator.validateComputer(computer);
    }

    @Test
    public void testCompanyValidation() throws ValidationException {
        Computer computer = new Computer.Builder("Computer 1").withCompany(new Company.Builder(1L).build()).build();
        validator.validateComputer(computer);
    }

    @Test(expected = InvalidDatesException.class)
    public void testDateUnvalidation() throws ValidationException {
        Computer computer = new Computer();
        computer.setName("Test Name");
        computer.setIntroduced(LocalDate.of(1995, 7, 21));
        computer.setDiscontinued(LocalDate.of(1969, 7, 21));
        validator.validateComputer(computer);
    }

    @Test
    public void testDateValidation() throws ValidationException {
        Computer computer = new Computer();
        computer.setName("Test Name");
        computer.setIntroduced(LocalDate.of(1969, 7, 21));
        computer.setDiscontinued(LocalDate.of(1995, 7, 21));
        validator.validateComputer(computer);
    }

    @Test(expected = NullNameException.class)
    public void testNameInvalidation() throws ValidationException {
        Computer computer = new Computer();
        validator.validateComputer(computer);
    }

    @Test
    public void testNameValidation() throws ValidationException {
        Computer computer = new Computer();
        computer.setName("Test Name");
        validator.validateComputer(computer);
    }
}
