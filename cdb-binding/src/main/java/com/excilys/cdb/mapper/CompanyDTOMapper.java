package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component("companyDTOMapper")
public class CompanyDTOMapper implements ICompanyDTOMapper {

    @Override
    public CompanyDTO createCompanyDTO(Company company) {
        if (company == null) {
            return null;
        }
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId().orElse(-1L));
        dto.setName(company.getName().orElse(""));
        return dto;
    }

    @Override
    public Company createCompanyFromDTO(CompanyDTO dto) {
        return new Company.Builder(dto.getId()).withName(dto.getName()).build();
    }
}
