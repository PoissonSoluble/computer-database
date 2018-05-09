package com.excilys.cdb.ui.webclient;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.ui.CommandLineInterface;
import com.excilys.cdb.ui.container.Page;

@Component
public class CompanyRESTPageHandler implements RESTPageHandler<Company> {

    private ICompanyDTOMapper companyDTOMapper;

    public CompanyRESTPageHandler(ICompanyDTOMapper pCompanyDTOMapper) {
        companyDTOMapper = pCompanyDTOMapper;
    }

    public Page<Company> getListFromREST(int page, int size) {
        List<Company> companies = new ArrayList<>();
        CommandLineInterface.client.target(CommandLineInterface.REST_URI)
                .path("companies/page/" + page + "/size/" + size).request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<CompanyDTO>>() {
                }).forEach(dto -> companies.add(companyDTOMapper.createCompanyFromDTO(dto)));
        int totalPage = CommandLineInterface.client.target(CommandLineInterface.REST_URI).path("companies/page/size/" + size + "/count")
                .request(MediaType.APPLICATION_JSON).get(Integer.class);
        return new Page<Company>(companies, page, totalPage);
    }
}
