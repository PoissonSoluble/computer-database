package com.excilys.cdb.validation;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface IComputerValidator {

    void validateComputer(Computer computer) throws ValidationException;

}