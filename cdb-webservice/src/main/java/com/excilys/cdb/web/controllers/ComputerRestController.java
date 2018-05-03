package com.excilys.cdb.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.IComputerService;

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

    @GetMapping("/computer/{id}")
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable Long id) {
        return new ResponseEntity<ComputerDTO>(
                computerDTOMapper.createComputerDTO(computerService.getComputer(id).orElse(new Computer())),
                HttpStatus.OK);
    }
}
