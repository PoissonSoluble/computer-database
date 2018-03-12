package com.excilys.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ComputerTest {

	private Computer computer;

	@BeforeEach
	public void setUp() {
		computer = new Computer();
		computer.setId((long) 1);
		computer.setName("Test");
		computer.setIntroduced(LocalDate.of(1995,7,21));
		computer.setDiscontinued(LocalDate.of(2018,5,3));
		computer.setCompany((long) 1);
	}

	@Test
	public void testCreation() {
		assertEquals(computer.getId(), 1);
		assertEquals(computer.getName(), "Test");
		assertEquals(computer.getIntroduced(), LocalDate.of(1995,7,21));
		assertEquals(computer.getDiscontinued(), LocalDate.of(2018,5,3));
		assertEquals(computer.getCompany(), 1);

	}

}
