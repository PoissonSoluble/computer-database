package com.excilys.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyTest {

	private Company company;
	
	@BeforeEach
	public void setUp() {
		company = new Company();
		
		company.setId(1);
		company.setName("Test");
	}
	
	@Test
	public void testCreation() {
		assertNotEquals(company, null, "Error while initiating Company class");
		
		
		assertEquals(company.getId(),1);
		assertEquals(company.getName(), "Test");
		
	}

}
