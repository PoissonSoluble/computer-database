package com.excilys.cdb.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.excilys.cdb.model.Company;

public class CompanyMapperTest {
    @Test
    public void testCreation() {
        Company company = CompanyMapper.INSTANCE.createCompany(1L, "Company");
        assertEquals(company.getId().get(), new Long(1L));
        assertEquals(company.getName().get(), "Company");
    }
}
