package com.excilys.cdb.dto;

import java.util.Arrays;
import java.util.List;

public enum CompanyOrdering {
    CA_ID("id", "ca_id", "companyId"),
    CA_NAME("name", "ca_name", "company_name");

    private List<String> validChoices;
    private String value;
    
    private CompanyOrdering(String pValue, String... pValidChoices) {
        value = pValue;
        validChoices = Arrays.asList(pValidChoices);
    }

    public boolean accept(String choice) {
        return validChoices.contains(choice.toLowerCase());
    }
    
    public String getValue() {
        return value;
    }
}
