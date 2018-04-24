package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public interface ICompanyDTOMapper {

    CompanyDTO createCompanyDTO(Company company);

}