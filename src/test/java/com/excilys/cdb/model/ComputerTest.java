package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;


public class ComputerTest {

	private Computer computer;

	public ComputerTest() {}
	
	@Before
	public void setUp() {
		Company company = new Company();
		computer = new Computer();
		computer.setId(new Long(1));
		computer.setName("Test");
		computer.setIntroduced(LocalDate.of(1995,7,21));
		computer.setDiscontinued(LocalDate.of(2018,5,3));
		company.setId(new Long(1));
		computer.setCompany(company);
	}

	@Test
	public void testCreation() {
		Company company = new Company();
		company.setId(new Long(1));
		assertEquals(computer.getId(), new Long(1));
		assertEquals(computer.getName(), "Test");
		assertEquals(computer.getIntroduced(), LocalDate.of(1995,7,21));
		assertEquals(computer.getDiscontinued(), LocalDate.of(2018,5,3));
		assertEquals(computer.getCompany(), company);

	}

}
