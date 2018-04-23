package com.excilys.cdb.springmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Controller
@RequestMapping("/addComputer")
public class CreateComputerController {

    private IComputerService computerService;
    private ICompanyService companyService;
    private IComputerDTOMapper computerDTOMapper;
    private ICompanyDTOMapper companyDTOMapper;

    public CreateComputerController(IComputerService pComputerService, ICompanyService pCompanyService,
            IComputerDTOMapper pComputerDTOMapper, ICompanyDTOMapper pCompanyDTOMapper) {
        computerService = pComputerService;
        companyService = pCompanyService;
        computerDTOMapper = pComputerDTOMapper;
        companyDTOMapper = pCompanyDTOMapper;
    }

    @GetMapping
    public ModelAndView handleGet(ModelAndView modelAndView) {
        List<CompanyDTO> companies = new ArrayList<>();
        companyService.getCompanies().forEach(company -> companies.add(companyDTOMapper.createCompanyDTO(company)));
        modelAndView.setViewName("addComputer");
        modelAndView.addObject("companies", companies);
        modelAndView.addObject("computerDTO", new ComputerDTO());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView handlePost(ComputerDTO dto) {
        ModelAndView modelAndView = new ModelAndView();
        Computer computer = computerDTOMapper.createComputerFromDTO(dto);   
        try {
            computerService.createComputer(computer);
        } catch (ValidationException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return handleGet(modelAndView);
    }
}
