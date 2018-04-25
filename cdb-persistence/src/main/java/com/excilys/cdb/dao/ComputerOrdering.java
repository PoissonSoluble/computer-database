package com.excilys.cdb.dao;

import java.util.Arrays;
import java.util.List;

public enum ComputerOrdering {
    CU_ID("id", "cu_id", "id", "computer_id"),
    CU_NAME("name", "cu_name", "name", "computer_name"),
    CU_INTRODUCED("introduced", "cu_introduced", "introduced", "computer_introduced"),
    CU_DISCONTINUED("discontinued", "cu_discontinued", "discontinued", "computer_discontinued"),
    CA_ID("company", "ca_id", "companyId"),
    CA_NAME("name", "ca_name", "company_name");

    private List<String> validChoices;
    private String value;
    
    private ComputerOrdering(String pValue, String... pValidChoices) {
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
