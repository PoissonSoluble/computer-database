package com.excilys.cdb.springmvc.controllers;

import java.time.LocalDate;
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
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {

    private IComputerService computerService;
    private ICompanyService companyService;
    private IComputerDTOMapper computerDTOMapper;
    private ICompanyDTOMapper companyDTOMapper;

    public EditComputerController(IComputerService pComputerService, ICompanyService pCompanyService,
            IComputerDTOMapper pComputerDTOMapper, ICompanyDTOMapper pCompanyDTOMapper) {
        computerService = pComputerService;
        companyService = pCompanyService;
        computerDTOMapper = pComputerDTOMapper;
        companyDTOMapper = pCompanyDTOMapper;
    }

    @GetMapping
    public ModelAndView handleGet(@RequestParam(value = "computerId", defaultValue = "-1") Long computerId, ModelAndView modelAndView) {
        if (computerId < 1) {
            return new ModelAndView("redirect:/dashboard");
        } else {
            Optional<Computer> computerOpt = computerService.getComputer(computerId);
            if (computerOpt.isPresent()) {
                return generateAndForwardDTOs(modelAndView, computerOpt);
            } else {
                return new ModelAndView("redirect:/dashboard");
            }
        }
    }

    @PostMapping
    public ModelAndView handlePost(@RequestParam(value = "id", required = true) Long computerId,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "introduced", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate discontinued,
            @RequestParam(value = "companyId", defaultValue = "-1") Long companyId) {
        ModelAndView modelAndView = new ModelAndView();
        Company company = null;
        if (companyId > 0) {
            company = new Company.Builder(companyId).build();
        }
        Computer computer = new Computer.Builder(computerId).withName(name).withIntroduced(introduced)
                .withDiscontinued(discontinued).withCompany(company).build();
        try {
            computerService.updateComputer(computer);
        } catch (ValidationException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return handleGet(computerId, modelAndView);
    }

    private ModelAndView generateAndForwardDTOs(ModelAndView modelAndView, Optional<Computer> computerOpt) {
        ComputerDTO computer = computerDTOMapper.createComputerDTO(computerOpt.get());
        List<CompanyDTO> companies = new ArrayList<>();
        companyService.getCompanies().forEach(company -> companies.add(companyDTOMapper.createCompanyDTO(company)));
        modelAndView.setViewName("editComputer");
        modelAndView.addObject("companies", companies);
        modelAndView.addObject("computer", computer);
        return modelAndView;
    }
}
