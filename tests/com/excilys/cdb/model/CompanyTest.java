package com.excilys.cdb.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyTest {

	private Company company;
	
	@BeforeEach
	public void setUp() {
		company = new Company();
		company.setId(new Long(1));
		company.setName("Test");
	}
	
	@Test
	public void testCreation() {
		assertNotNull(company);
		assertEquals(company.getId(),new Long(1));
		assertEquals(company.getName(), "Test");
	}

}
