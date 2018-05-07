package com.excilys.cdb.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.service.ICompanyService;

@RestController
public class CompanyRestController {

    private ICompanyService companyService;
    private ICompanyDTOMapper companyDTOMapper;

    public CompanyRestController(ICompanyService pCompanyService, ICompanyDTOMapper pCompanyDTOMapper) {
        companyService = pCompanyService;
        companyDTOMapper = pCompanyDTOMapper;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        companyService.getCompanies().forEach(company -> companyDTOs.add(companyDTOMapper.createCompanyDTO(company)));
        return new ResponseEntity<List<CompanyDTO>>(companyDTOs, HttpStatus.OK);
    }

    @GetMapping("/companies/page/{page}/size/{size}")
    public ResponseEntity<List<CompanyDTO>> getCompanyPage(@PathVariable int page, @PathVariable int size) {
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        try {
            companyService.getPage(page, size, "")
                    .forEach(company -> companyDTOs.add(companyDTOMapper.createCompanyDTO(company)));
            return new ResponseEntity<List<CompanyDTO>>(companyDTOs, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(companyDTOs, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/companies/page/size/{size}/count")
    public ResponseEntity<Integer> getCompanyPageCount(@PathVariable int size) {
        return new ResponseEntity<Integer>(companyService.getPage(0, size, "").getTotalPages(), HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<String>("OK.", HttpStatus.OK);
    }

}
