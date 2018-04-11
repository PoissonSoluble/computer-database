package com.excilys.cdb.servlet;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.CompanyDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

@WebServlet(name = "CreateComputerServlet", urlPatterns = "/createComputer")
public class CreateComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    ComputerService computerService;
    @Autowired
    CompanyService companyService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private void executeCreation(HttpServletRequest request, Computer computer) {
        try {
            computerService.createComputer(computer);
        } catch (NullNameException e) {
            request.setAttribute("error", "The name cannot be empty.");
        } catch (NotExistingCompanyException e) {
            request.setAttribute("error", "The company does not exist in the database.");
        } catch (InvalidDatesException e) {
            request.setAttribute("error", "The dates are not valid.");
        } catch (ValidationException e) {
            request.setAttribute("error", "There was a problem while validating the data. Please check your entries.");
        }
    }

    private LocalDate getDate(String dateString) {
        try {
            return LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException | NullPointerException e) {
            return null;
        }
    }

    private Optional<Long> getLongParam(HttpServletRequest request, String param) {
        try {
            return Optional.ofNullable(Long.parseLong(request.getParameter(param)));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CompanyDTOMapper mapper = new CompanyDTOMapper();
        List<CompanyDTO> companies = new ArrayList<>();
        try {
            companyService.getCompanies().forEach(company -> companies.add(mapper.createCompanyDTO(company)));
        } catch (ServiceException e) {
            throw new ServletException("Error while getting the company DTOs.");
        }
        request.setAttribute("companies", companies);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/addComputer.jsp");
        view.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        LocalDate introduced = getDate(request.getParameter("introduced"));
        LocalDate discontinued = getDate(request.getParameter("discontinued"));
        Optional<Long> companyID = getLongParam(request, "companyId");
        Company company = null;
        if (companyID.isPresent()) {
            company = new Company.Builder(companyID.get()).build();
        }
        Computer computer = new Computer.Builder(name).withIntroduced(introduced).withDiscontinued(discontinued)
                .withCompany(company).build();
        executeCreation(request, computer);
        doGet(request, response);
    }
}
