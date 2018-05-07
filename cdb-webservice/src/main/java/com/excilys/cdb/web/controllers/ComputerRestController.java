package com.excilys.cdb.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.dto.ComputerDTO;
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
    public ResponseEntity<List<ComputerDTO>> getComputers() {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getComputers()
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/order/{order}/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputersWithOrdering(@PathVariable ComputerOrdering order,
            @PathVariable Direction ascending) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getComputersWithOrder(order, ascending)
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/search/{search}")
    public ResponseEntity<List<ComputerDTO>> getComputersBySearch(@PathVariable String search) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getComputersBySearch(search)
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/search/{search}/order/{order}/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputersBySearchWithOrdering(@PathVariable String search,
            @PathVariable ComputerOrdering order, @PathVariable Direction ascending) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getComputersBySearchWithOrder(search, order, ascending)
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/page/{page}/size/{size}")
    public ResponseEntity<List<ComputerDTO>> getComputersPage(@PathVariable int page, @PathVariable int size) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getPage(page, size, "")
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/page/{page}/size/{size}/order/{order}/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputersPageWithOrder(@PathVariable int page, @PathVariable int size,
            @PathVariable ComputerOrdering order, @PathVariable Direction ascending) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getPage(page, size, "", order, ascending)
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/page/{page}/size/{size}/search/{search}")
    public ResponseEntity<List<ComputerDTO>> getComputersPageBySearch(@PathVariable int page, @PathVariable int size,
            @PathVariable String search) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getPage(page, size, search)
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/page/{page}/size/{size}/search/{search}/order/{order}/{ascending}")
    public ResponseEntity<List<ComputerDTO>> getComputersPageBySearchWithOrder(@PathVariable int page,
            @PathVariable int size, @PathVariable String search, @PathVariable ComputerOrdering order,
            @PathVariable Direction ascending) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getPage(page, size, search, order, ascending)
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/count")
    public ResponseEntity<Integer> getCount() {
        return new ResponseEntity<Integer>(computerService.getComputerAmount(""), HttpStatus.OK);
    }

    @GetMapping("/computers/count/search/{search}")
    public ResponseEntity<Integer> getCountBySearch(@PathVariable String search) {
        return new ResponseEntity<Integer>(computerService.getComputerAmount(search), HttpStatus.OK);
    }

    @GetMapping("/computers/page/size/{size}/count")
    public ResponseEntity<Integer> getComputersPageCount(@PathVariable int size) {
        return new ResponseEntity<Integer>(computerService.getPage(0, size, "").getTotalPages(), HttpStatus.OK);
    }

    @GetMapping("/computers/page/size/{size}/search/{search}/count")
    public ResponseEntity<Integer> getComputersPageCountBySearch(@PathVariable int size, @PathVariable String search) {
        return new ResponseEntity<Integer>(computerService.getPage(0, size, search).getTotalPages(), HttpStatus.OK);
    }

    @GetMapping("/computer/{id}")
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable Long id) {
        return new ResponseEntity<ComputerDTO>(
                computerDTOMapper.createComputerDTO(computerService.getComputer(id).orElse(new Computer())),
                HttpStatus.OK);
    }

    @PostMapping("/computer")
    public ResponseEntity<String> postComputer(@RequestBody ComputerDTO computerDTO) {
        Computer computer = computerDTOMapper.createComputerFromDTO(computerDTO);
        try {
            computerService.createComputer(computer);
        } catch (ValidationException e) {
            return new ResponseEntity<String>("Bad request.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("OK.", HttpStatus.OK);
    }

    @PutMapping("/computer")
    public ResponseEntity<String> putComputer(@RequestBody ComputerDTO computerDTO) {
        Computer computer = computerDTOMapper.createComputerFromDTO(computerDTO);
        try {
            computerService.updateComputer(computer);
        } catch (ValidationException e) {
            return new ResponseEntity<String>("Bad request.", HttpStatus.BAD_REQUEST);
        } catch (ServiceException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("OK.", HttpStatus.OK);
    }

    @DeleteMapping("/computer/{id}")
    public ResponseEntity<String> deleteComputer(@PathVariable Long id) {
        computerService.deleteComputer(id);
        return new ResponseEntity<String>("OK.", HttpStatus.OK);
    }
}
