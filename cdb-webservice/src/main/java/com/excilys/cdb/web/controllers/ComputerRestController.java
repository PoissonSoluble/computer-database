package com.excilys.cdb.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@RestController
public class ComputerRestController {

    private IComputerService computerService;
    private IComputerDTOMapper computerDTOMapper;

    public ComputerRestController(IComputerService pComputerService, IComputerDTOMapper pComputerDTOMapper) {
        computerService = pComputerService;
        computerDTOMapper = pComputerDTOMapper;
    }

    @GetMapping("/computers")
    public ResponseEntity<List<ComputerDTO>> getComputers() {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getComputers()
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }

    @GetMapping("/computers/page/{page}/{size}")
    public ResponseEntity<List<ComputerDTO>> getComputersPage(@PathVariable int page, @PathVariable int size) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.getPage(page, size, "")
                .forEach(computer -> computerDTOs.add(computerDTOMapper.createComputerDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
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
