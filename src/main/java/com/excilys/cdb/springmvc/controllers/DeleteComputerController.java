package com.excilys.cdb.springmvc.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.service.IComputerService;

@Controller
@RequestMapping("/deleteComputer")
public class DeleteComputerController {

    private IComputerService computerService;

    public DeleteComputerController(IComputerService pComputerService) {
        computerService = pComputerService;
    }

    @GetMapping
    public ModelAndView handleGet(@RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
        modelAndView.addObject("pageNumber", pageNumber);
        modelAndView.addObject("pageSize", pageSize);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView handlePost(@RequestParam(name = "selection", defaultValue = "") String selection,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        List<Long> ids = new ArrayList<>();
        for (String idString : selection.split(",")) {
            ids.add(Long.parseLong(idString));
        }
        computerService.deleteComputers(ids);
        return handleGet(pageNumber, pageSize);
    }
}
