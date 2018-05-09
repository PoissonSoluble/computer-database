package com.excilys.cdb.ui.webclient;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.cdb.ui.CommandLineInterface;

@Component
public class CompanyRequestHandler {

    public int deleteCompany(Long id) {
        return CommandLineInterface.client.target(CommandLineInterface.REST_URI).path("company/" + id)
                .request(MediaType.APPLICATION_JSON).delete().getStatus();
    }
}
