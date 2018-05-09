package com.excilys.cdb.ui.webclient;

import java.util.Optional;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.CommandLineInterface;

@Component
public class ComputerRequestHandler {

    private IComputerDTOMapper computerDTOMapper;

    public ComputerRequestHandler(IComputerDTOMapper pComputerDTOMapper) {
        computerDTOMapper = pComputerDTOMapper;
    }

    public Optional<Computer> getComputer(Long id) {
        return Optional.ofNullable(computerDTOMapper
                .createComputerFromDTO(CommandLineInterface.client.target(CommandLineInterface.REST_URI)
                        .path("computer/" + id).request(MediaType.APPLICATION_JSON).get(ComputerDTO.class)));
    }

    public int postComputer(Computer computer) {
        return CommandLineInterface.client.target(CommandLineInterface.REST_URI).path("computer")
                .request(MediaType.APPLICATION_JSON).post(Entity.json(computerDTOMapper.createComputerDTO(computer)))
                .getStatus();
    }

    public int putComputer(Computer computer) {
        return CommandLineInterface.client.target(CommandLineInterface.REST_URI).path("computer")
                .request(MediaType.APPLICATION_JSON).put(Entity.json(computerDTOMapper.createComputerDTO(computer)))
                .getStatus();
    }
}
