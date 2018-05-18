package com.excilys.cdb.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.ComputerOrdering;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.ValidationException;
import com.excilys.cdb.web.util.ComputerOrderingCaseConverter;
import com.excilys.cdb.web.util.DirectionCaseConverter;

@RestController
public class ComputerRestController {

    private IComputerService computerService;
    private IComputerDTOMapper computerDTOMapper;

    public ComputerRestController(IComputerService pComputerService, IComputerDTOMapper pComputerDTOMapper) {
        computerService = pComputerService;
        computerDTOMapper = pComputerDTOMapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(ComputerOrdering.class, new ComputerOrderingCaseConverter());
        binder.registerCustomEditor(Direction.class, new DirectionCaseConverter());
    }

    @GetMapping("/computers")
    public ResponseEntity<List<ComputerDTO>> getComputers(
            @RequestParam(name = "page-number", required = false) Optional<Integer> pageNumber,
            @RequestParam(name = "page-size", required = false) Optional<Integer> pageSize,
            @RequestParam(name = "search", defaultValue = "") String search,
            @RequestParam(name = "order", defaultValue = "cu_id") ComputerOrdering order,
            @RequestParam(name = "direction", defaultValue = "asc") Direction direction) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        if (pageNumber.isPresent() && pageSize.isPresent()) {
            computerService.getPage(pageNumber.get(), pageSize.get(), search, order, direction)
                    .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        } else {
            computerService.getComputersBySearchWithOrder(search, order, direction)
                    .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        }
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/count")
    public ResponseEntity<Integer> getCount(
            @RequestParam(name = "page-size", required = false) Optional<Integer> pageSize,
            @RequestParam(name = "search", defaultValue = "") String search) {
        if (pageSize.isPresent()) {
            return new ResponseEntity<Integer>(computerService.getPage(0, pageSize.get(), search).getTotalPages(),
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<Integer>(computerService.getComputerCount(search), HttpStatus.OK);
        }
    }

    @GetMapping("/computers/page/count")
    public ResponseEntity<Integer> getPageCount(@RequestParam(name = "page-size", required = true) Integer pageSize,
            @RequestParam(name = "search", defaultValue = "") String search) {
        return new ResponseEntity<Integer>(computerService.getPage(0, pageSize, search).getTotalPages(), HttpStatus.OK);

    }

    @GetMapping("/computers/{id}")
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable Long id) {
        return new ResponseEntity<ComputerDTO>(
                computerDTOMapper.createComputerDTO(computerService.getComputer(id).orElse(new Computer())),
                HttpStatus.OK);
    }

    @PostMapping("/computers")
    public ResponseEntity<String> postComputer(@RequestBody ComputerDTO computerDTO) {
        Computer computer = computerDTOMapper.createComputerFromDTO(computerDTO);
        try {
            computerService.createComputer(computer);
            return new ResponseEntity<String>("OK.", HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<String>("Bad request.", HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/computers")
    public ResponseEntity<String> putComputer(@RequestBody ComputerDTO computerDTO) {
        Computer computer = computerDTOMapper.createComputerFromDTO(computerDTO);
        try {
            computerService.updateComputer(computer);
            return new ResponseEntity<String>("OK.", HttpStatus.OK);
        } catch (ValidationException | ServiceException e) {
            return new ResponseEntity<String>("Bad request." + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/computer/{id}")
    public ResponseEntity<String> deleteComputer(@PathVariable Long id) {
        computerService.deleteComputer(id);
        return new ResponseEntity<String>("Accepted.", HttpStatus.ACCEPTED);
    }
}
