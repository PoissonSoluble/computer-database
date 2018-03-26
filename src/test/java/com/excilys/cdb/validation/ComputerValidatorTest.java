package com.excilys.cdb.validation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.cdb.mockdb.MockDataBase;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

public class ComputerValidatorTest {
    
    @AfterClass
    public static void destroy() {
        MockDataBase.removeDataBase();
    }

    @BeforeClass
    public static void setUp() {
        MockDataBase.createDatabase();
    }

    private ComputerValidator validator = ComputerValidator.INSTANCE;

    public ComputerValidatorTest() {
    }

    @Test
    public void testDateUnvalidation() {
        Computer computer = new Computer();
        computer.setName("Test Name");
        computer.setIntroduced(LocalDate.of(1995, 7, 21));
        computer.setDiscontinued(LocalDate.of(1969, 7, 21));
        try {
            validator.validateComputer(computer);
        } catch (InvalidDatesException e) {
            assertTrue(true);
            return;
        } catch (ValidationException e) {
            fail("The exception should be a InvalidDatesException.");
        }
        fail("The computer should not be validated with introduced superior to discontinued.");
    }

    @Test
    public void testDateValidation() {
        Computer computer = new Computer();
        computer.setName("Test Name");
        computer.setIntroduced(LocalDate.of(1969, 7, 21));
        computer.setDiscontinued(LocalDate.of(1995, 7, 21));
        try {
            validator.validateComputer(computer);
        } catch (ValidationException e) {
            fail("This computer should be validated but throws an exception.");
        }
        assertTrue(true);
    }

    @Test
    public void testNameInvalidation() {
        Computer computer = new Computer();
        try {
            validator.validateComputer(computer);
        } catch (NullNameException e) {
            assertTrue(true);
            return;
        } catch (ValidationException e) {
            fail("The exception should be a NullNameException.");
        }
        fail("The computer should not be validated with a null name.");
    }

    @Test
    public void testNameValidation() {
        Computer computer = new Computer();
        computer.setName("Test Name");
        try {
            validator.validateComputer(computer);
        } catch (ValidationException e) {
            fail("Should validate with simply the name");
        }
        assertTrue(true);
    }
    
    @Test
    public void testCompanyValidation() {
        Computer computer = new Computer.Builder("Computer 1").withCompany(new Company.Builder(1L).build()).build();
        try {
            validator.validateComputer(computer);
        }  catch (ValidationException e) {
            fail("Should validate with this company.");
        }
        assertTrue(true);
    }
    
    
    @Test
    public void testCompanyInvalidation() {
        Computer computer = new Computer.Builder("Computer 1").withCompany(new Company.Builder(50L).build()).build();
        try {
            validator.validateComputer(computer);
        } catch (NotExistingCompanyException e) {
            assertTrue(true);
            return;
        } catch (ValidationException e) {
            fail("The exception should be a NotExistingCompanyException.");
        }
        fail("The computer should not be validated with this company id.");
    }
}
