package com.excilys.cdb.validation;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

class ComputerValidatorTest {

	private ComputerValidator validator = ComputerValidator.INSTANCE;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	void testNameValidation() {
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
	void testNameInvalidation() {
		Computer computer = new Computer();
		try {
			validator.validateComputer(computer);
		} catch(NullNameException e){
			assertTrue(true);
			return;
		} catch(ValidationException e) {
			fail("The exception should be a NullNameException.");
		}
		fail("The computer should not be validated with a null name.");
	}
	
	@Test
	void testTODO() {
		Computer computer = new Computer();
		try {
			validator.validateComputer(computer);
		} catch(NullNameException e){
			assertTrue(true);
			return;
		} catch(ValidationException e) {
			fail("The exception should be a NullNameException.");
		}
		fail("The computer should not be validated with a null name.");
	}
}
