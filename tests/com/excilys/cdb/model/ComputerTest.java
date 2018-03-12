package com.excilys.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ComputerTest {

	private Computer computer;
	private Company company;

	@BeforeEach
	public void setUp() {
		computer = new Computer();
		computer.setId((long) 1);
		computer.setName("Test");
		computer.setIntroduced(new GregorianCalendar(1995,7,21).getTime());
		computer.setDiscontinued(new GregorianCalendar(2018,5,3).getTime());

		company = new Company();

		company.setId(1);
		company.setName("Test");

		computer.setCompany(company);
	}

	@Test
	public void testCreation() {
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Test");
		assertEquals(computer.getIntroduced(), new GregorianCalendar(1995,7,21).getTime());
		assertEquals(computer.getDiscontinued(), new GregorianCalendar(2018,5,3).getTime());
		assertEquals(computer.getCompany(), company);

	}

}
