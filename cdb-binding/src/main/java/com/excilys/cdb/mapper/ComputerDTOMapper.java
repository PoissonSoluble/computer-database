package com.excilys.cdb.mapper;

import java.time.DateTimeException;
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
        dto.setCompany(companyMapper.createCompanyDTO(computer.getCompany().orElse(null)));
        return dto;
    }

    public Computer createComputerFromDTO(ComputerDTO dto) {
        LocalDate introduced = getDate(dto.getIntroduced());
        LocalDate discontinued = getDate(dto.getDiscontinued());
        Company company = null;
        if (dto.getCompany().getId() == 0) {
            company = null;
        } else {
            company = new Company.Builder(dto.getCompany().getId()).withName(dto.getCompany().getName()).build();
        }
        return new Computer.Builder(dto.getId()).withName(dto.getName()).withIntroduced(introduced)
                .withDiscontinued(discontinued).withCompany(company).build();
    }

    private String getFormattedDate(Optional<LocalDate> date) {
        if (date.isPresent()) {
            return date.get().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return "";
        }
    }

    private LocalDate getDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException | NullPointerException e) {
            return null;
        }
    }
}
