package com.excilys.cdb.springmvc.controllers;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Controller
@RequestMapping("/addComputer")
public class CreateComputerController {

    private IComputerService computerService;
    private ICompanyService companyService;
    private ICompanyDTOMapper companyDTOMapper;

    public CreateComputerController(IComputerService pComputerService, ICompanyService pCompanyService,
            ICompanyDTOMapper pCompanyDTOMapper) {
        computerService = pComputerService;
        companyService = pCompanyService;
        companyDTOMapper = pCompanyDTOMapper;
    }

    @GetMapping
    public ModelAndView handleGet(ModelAndView modelAndView) {
        List<CompanyDTO> companies = new ArrayList<>();
        companyService.getCompanies().forEach(company -> companies.add(companyDTOMapper.createCompanyDTO(company)));
        modelAndView.setViewName("addComputer");
        modelAndView.addObject("companies", companies);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView handlePost(@RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "introduced", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate discontinued,
            @RequestParam(value = "companyId", defaultValue = "-1") Long companyId) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = null;
        if (companyId > 0) {
            company = new Company.Builder(companyId).build();
        }
        Computer computer = new Computer.Builder(name).withIntroduced(introduced).withDiscontinued(discontinued)
                .withCompany(company).build();
        try {
            computerService.createComputer(computer);
        } catch (ValidationException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return handleGet(modelAndView);
    }
}
