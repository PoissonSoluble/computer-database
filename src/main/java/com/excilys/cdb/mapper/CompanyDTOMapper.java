package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyDTOMapper {
    public CompanyDTO createCompanyDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId().orElse(-1L));
        dto.setName(company.getName().orElse(""));
        return dto;
    }
}
