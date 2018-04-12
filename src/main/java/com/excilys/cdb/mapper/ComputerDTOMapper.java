package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component("computerDTOMapper")
public class ComputerDTOMapper implements IComputerDTOMapper {

    @Override
    public ComputerDTO createComputerDTO(Computer computer) {
        ComputerDTO dto = new ComputerDTO();
        ICompanyDTOMapper companyMapper = new CompanyDTOMapper();
        dto.setId(computer.getId().orElse(0L));
        dto.setName(computer.getName().orElse(""));
        dto.setIntroduced(getFormattedDate(computer.getIntroduced()));
        dto.setDiscontinued(getFormattedDate(computer.getDiscontinued()));
        dto.setCompany(companyMapper.createCompanyDTO(computer.getCompany().orElse(new Company.Builder("").build())));
        return dto;
    }

    private String getFormattedDate(Optional<LocalDate> date) {
        if (date.isPresent()) {
            return date.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return "";
        }
    }
}
