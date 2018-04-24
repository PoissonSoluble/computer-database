package com.excilys.cdb.dao;

import java.util.Arrays;
import java.util.List;

public enum ComputerOrdering {
    CU_ID("cu_id", "id", "computer_id"),
    CU_NAME("cu_name", "name", "computer_name"),
    CU_INTRODUCED("cu_introduced", "introduced", "computer_introduced"),
    CU_DISCONTINUED("cu_discontinued", "discontinued", "computer_discontinued"),
    CA_ID("ca_id", "companyId"),
    CA_NAME("ca_name", "company_name");

    private List<String> validChoices;

    private ComputerOrdering(String... pValidChoices) {
        validChoices = Arrays.asList(pValidChoices);
    }

    public boolean accept(String choice) {
        return validChoices.contains(choice.toLowerCase());
    }
}
