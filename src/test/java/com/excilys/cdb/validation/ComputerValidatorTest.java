package com.excilys.cdb.validation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

public class ComputerValidatorTest {

	private ComputerValidator validator = ComputerValidator.INSTANCE;

	public ComputerValidatorTest() {
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
	public void testTODO() {
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
}
