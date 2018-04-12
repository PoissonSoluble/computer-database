package com.excilys.cdb.mapper;

import java.sql.Date;

import com.excilys.cdb.model.Computer;

public interface IComputerMapper {

    Computer createComputer(long id, String name, Date introduced, Date discontinued, long companyId,
            String companyName);

}