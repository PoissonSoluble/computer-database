package com.excilys.cdb.ui.webclient;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.CommandLineInterface;
import com.excilys.cdb.ui.container.Page;

@Component
public class ComputerRESTPageHandler implements RESTPageHandler<Computer> {

    private IComputerDTOMapper computerDTOMapper;

    public ComputerRESTPageHandler(IComputerDTOMapper pComputerDTOMapper) {
        computerDTOMapper = pComputerDTOMapper;
    }

    @Override
    public Page<Computer> getListFromREST(int page, int size) {
        List<Computer> computers = new ArrayList<>();
        CommandLineInterface.client.target(CommandLineInterface.REST_URI)
                .path("computers/page/" + page + "/size/" + size).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ComputerDTO>>() {
                }).forEach(dto -> computers.add(computerDTOMapper.createComputerFromDTO(dto)));
        int totalPage = CommandLineInterface.client.target(CommandLineInterface.REST_URI)
                .path("computers/page/size/" + size + "/count").request(MediaType.APPLICATION_JSON).get(Integer.class);
        System.out.println("TOTAL PAGE = "+ totalPage + " size : " + size);
        return new Page<Computer>(computers, page, totalPage);
    }

}
