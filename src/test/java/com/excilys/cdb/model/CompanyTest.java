package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;

public class CompanyTest {

	private Company company;
	
	public CompanyTest() {}
	
	@Before
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
