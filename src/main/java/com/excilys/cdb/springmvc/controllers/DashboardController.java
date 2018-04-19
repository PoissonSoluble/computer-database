package com.excilys.cdb.springmvc.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.pagination.ComputerPage;
import com.excilys.cdb.service.IComputerService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private IComputerService computerService;
    private IComputerDTOMapper computerDTOMapper;
    private MessageSource messageSource;

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SIZE = "10";
    private static final String DEFAULT_ORDER = "CU_ID";
    private static final String DEFAULT_ASCENDING = "true";
    private static final String DEFAULT_SEARCH = "";

    @Autowired
    public DashboardController(IComputerService pComputerService, IComputerDTOMapper pComputerDTOMapper,
            MessageSource pMessageSource) {
        computerService = pComputerService;
        computerDTOMapper = pComputerDTOMapper;
        messageSource = pMessageSource;
    }

    @GetMapping
    public ModelAndView handleGet(@RequestParam(value = "pageNumber", defaultValue = DEFAULT_PAGE) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_SIZE) int pageSize,
            @RequestParam(value = "ascending", defaultValue = DEFAULT_ASCENDING) boolean ascending,
            @RequestParam(value = "order", defaultValue = DEFAULT_ORDER) String order,
            @RequestParam(value = "search", defaultValue = DEFAULT_SEARCH) String search, Locale locale) {

        ComputerOrdering computerOrder = Stream.of(ComputerOrdering.values()).filter(v -> v.accept(order)).findFirst()
                .orElse(ComputerOrdering.CU_ID);
        ComputerPage page = new ComputerPage(pageNumber, pageSize, search, computerOrder, ascending, computerService);
        List<ComputerDTO> computerDTOs = getDTOsFromPage(page);
        ModelAndView modelAndView = new ModelAndView("dashboard");
        setModelAttributes(pageSize, ascending, order, search, page, computerDTOs, modelAndView);
        return modelAndView;
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
    
    private List<ComputerDTO> getDTOsFromPage(ComputerPage page) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        page.get().forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return computerDTOs;
    }
}
