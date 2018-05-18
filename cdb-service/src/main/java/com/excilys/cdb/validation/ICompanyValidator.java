package com.excilys.cdb.validation;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.validation.exceptions.ValidationException;

public interface ICompanyValidator {

    void validateCompany(Company company) throws ValidationException;

}