package com.excilys.cdb.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerOrdering;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.ValidationException;
import com.excilys.cdb.web.util.ComputerOrderingCaseConverter;
import com.excilys.cdb.web.util.DirectionCaseConverter;

@CrossOrigin
@RestController
public class CompanyRestController {

    private ICompanyService companyService;
    private ICompanyDTOMapper companyDTOMapper;

    public CompanyRestController(ICompanyService pCompanyService, ICompanyDTOMapper pCompanyDTOMapper) {
        companyService = pCompanyService;
        companyDTOMapper = pCompanyDTOMapper;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ComputerOrdering.class, new ComputerOrderingCaseConverter());
        binder.registerCustomEditor(Direction.class, new DirectionCaseConverter());
    }


    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies(
            @RequestParam(name = "page-number", required = false) Optional<Integer> pageNumber,
            @RequestParam(name = "page-size", required = false) Optional<Integer> pageSize,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "order", defaultValue = "cu_id") ComputerOrdering order,
            @RequestParam(name = "direction", defaultValue = "asc") Direction direction) {
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            companyService.getPage(pageNumber.get(), pageSize.get(), search)
                    .forEach(company -> companyDTOs.add(companyDTOMapper.createCompanyDTO(company)));
        } else {
            companyService.getCompaniesBySearchWithOrder(search, order, direction)
                    .forEach(company -> companyDTOs.add(companyDTOMapper.createCompanyDTO(company)));

        }
        return new ResponseEntity<List<CompanyDTO>>(companyDTOs, HttpStatus.OK);
    }
    

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Long id) {
        return new ResponseEntity<CompanyDTO>(
                companyDTOMapper.createCompanyDTO(companyService.getCompany(id).orElse(new Company())),
                HttpStatus.OK);
    }

    @GetMapping("/companies/count")
    public ResponseEntity<Integer> getCompanyPageCount(
            @RequestParam(name = "search", defaultValue = "") String search) {
        return new ResponseEntity<Integer>(companyService.getCompanyCount(search), HttpStatus.OK);
    }

    @GetMapping("/companies/page/count")
    public ResponseEntity<Integer> getPageCount(@RequestParam(name = "page-size", required = true) Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search) {
        return new ResponseEntity<Integer>(companyService.getPage(0, pageSize, search).getTotalPages(), HttpStatus.OK);

    }

    @PostMapping("/companies")
    public ResponseEntity<String> postCompany(@RequestBody CompanyDTO companyDTO) {
        Company company = companyDTOMapper.createCompanyFromDTO(companyDTO);
        try {
            companyService.createCompany(company);
            return new ResponseEntity<String>("OK.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<String>("Bad request.", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/companies")
    public ResponseEntity<String> putComputer(@RequestBody CompanyDTO companyDTO) {
        Company company = companyDTOMapper.createCompanyFromDTO(companyDTO);
        try {
            companyService.updateCompany(company);
            return new ResponseEntity<String>("OK.", HttpStatus.OK);
        } catch (ValidationException | ServiceException e) {
            return new ResponseEntity<String>("Bad request." + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<String>("OK.", HttpStatus.OK);
    }

}
