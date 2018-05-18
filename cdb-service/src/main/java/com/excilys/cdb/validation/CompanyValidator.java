package com.excilys.cdb.validation;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@Component("companyValidator")
public class CompanyValidator implements ICompanyValidator {

    @Override
    public void validateCompany(Company company) throws ValidationException {
        validateName(company.getName());
    }
    
    private void validateName(Optional<String> name) throws NullNameException {
        name.orElseThrow(() -> new NullNameException());
    }
}
