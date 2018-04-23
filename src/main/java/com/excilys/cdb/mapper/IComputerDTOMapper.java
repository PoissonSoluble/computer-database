package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;

public interface IComputerDTOMapper {

    ComputerDTO createComputerDTO(Computer computer);

    Computer createComputerFromDTO(ComputerDTO dto);

}