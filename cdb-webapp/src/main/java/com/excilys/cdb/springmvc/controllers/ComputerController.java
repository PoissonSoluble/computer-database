package com.excilys.cdb.springmvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.ComputerPage;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SIZE = "10";
    private static final String DEFAULT_ORDER = "CU_ID";
    private static final String DEFAULT_ASCENDING = "true";

    private static final String DEFAULT_SEARCH = "";
    private IComputerService computerService;
    private ICompanyService companyService;
    private IComputerDTOMapper computerDTOMapper;
    private ICompanyDTOMapper companyDTOMapper;

    @Autowired
    public ComputerController(IComputerService pComputerService, ICompanyService pCompanyService,
            IComputerDTOMapper pComputerDTOMapper, ICompanyDTOMapper pCompanyDTOMapper) {
        computerService = pComputerService;
        companyService = pCompanyService;
        computerDTOMapper = pComputerDTOMapper;
        companyDTOMapper = pCompanyDTOMapper;
    }

    @GetMapping("/add")
    public ModelAndView getCreate(ModelAndView modelAndView) {
        List<CompanyDTO> companies = new ArrayList<>();
        companyService.getCompanies().forEach(company -> companies.add(companyDTOMapper.createCompanyDTO(company)));
        modelAndView.setViewName("addComputer");
        modelAndView.addObject("companies", companies);
        modelAndView.addObject("computerDTO", new ComputerDTO());
        return modelAndView;
    }

    @GetMapping("/delete")
    public ModelAndView getDelete(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("redirect:/computer/dashboard");
        modelAndView.addObject("pageNumber", pageNumber);
        modelAndView.addObject("pageSize", pageSize);
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView getEdit(@RequestParam(value = "computerId", defaultValue = "-1") Long computerId,
            ModelAndView modelAndView) {
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

    @GetMapping("/dashboard")
    public ModelAndView handleGet(@RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_SIZE) int pageSize,
            @RequestParam(value = "ascending", defaultValue = DEFAULT_ASCENDING) boolean ascending,
            @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) String order,
            @RequestParam(value = "search", defaultValue = DEFAULT_SEARCH) String search) {

        ComputerOrdering computerOrder = Stream.of(ComputerOrdering.values()).filter(v -> v.accept(order)).findFirst()
                .orElse(ComputerOrdering.CU_ID);
        Direction direction = ascending ? Direction.ASC : Direction.DESC;
        ComputerPage page = new ComputerPage(pageNumber, pageSize, search, computerOrder, direction, computerService);
        List<ComputerDTO> computerDTOs = getDTOsFromPage(page);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        setModelAttributes(pageSize, ascending, order, search, page, computerDTOs, modelAndView);
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView postCreate(ComputerDTO dto) {
        ModelAndView modelAndView = new ModelAndView();
        Computer computer = computerDTOMapper.createComputerFromDTO(dto);
        try {
            computerService.createComputer(computer);
        } catch (ValidationException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return getCreate(modelAndView);
    }

    @PostMapping("/delete")
    public ModelAndView postDelete(@RequestParam(name = "selection", defaultValue = "") String selection,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        List<Computer> computers = new ArrayList<>();
        for (String idString : selection.split(",")) {
            computers.add(new Computer.Builder(Long.parseLong(idString)).build());
        }
        computerService.deleteComputers(computers);
        return getDelete(pageNumber, pageSize);
    }

    @PostMapping("/edit")
    public ModelAndView postEdit(ComputerDTO dto) {
        ModelAndView modelAndView = new ModelAndView();
        Computer computer = computerDTOMapper.createComputerFromDTO(dto);
        try {
            computerService.updateComputer(computer);
        } catch (ValidationException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return getEdit(computer.getId().orElse(0L), modelAndView);
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

    private List<ComputerDTO> getDTOsFromPage(ComputerPage page) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        page.get().forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return computerDTOs;
    }

    private void setModelAttributes(int pageSize, boolean ascending, String order, String search, ComputerPage page,
            List<ComputerDTO> computerDTOs, ModelAndView modelAndView) {
        modelAndView.addObject("computers", computerDTOs);
        modelAndView.addObject("pageNumber", page.getPageNumber());
        modelAndView.addObject("totalPage", page.getPageTotal());
        modelAndView.addObject("search", search);
        modelAndView.addObject("computerAmount", computerService.getComputerAmount(search));
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("order", order.toString());
        modelAndView.addObject("ascending", ascending);
    }
}
