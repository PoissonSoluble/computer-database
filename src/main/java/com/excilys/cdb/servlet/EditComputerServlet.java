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
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ICompanyDTOMapper;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ICompanyService;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;
import com.excilys.cdb.validation.exceptions.InvalidDatesException;
import com.excilys.cdb.validation.exceptions.NotExistingCompanyException;
import com.excilys.cdb.validation.exceptions.NullNameException;
import com.excilys.cdb.validation.exceptions.ValidationException;

//@WebServlet(name = "EditComputerServlet", urlPatterns = "/editComputer")
public class EditComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private IComputerService computerService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IComputerDTOMapper computerMapper;
    @Autowired
    private ICompanyDTOMapper companyMapper;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    private void executeUpdate(HttpServletRequest request, Computer computer) {
        try {
            computerService.updateComputer(computer);
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

    private void generateAndForwardDTOs(HttpServletRequest request, HttpServletResponse response,
            Optional<Computer> computerOpt) throws ServletException, IOException {
        ComputerDTO computer = computerMapper.createComputerDTO(computerOpt.get());
        List<CompanyDTO> companies = new ArrayList<>();
        companyService.getCompanies().forEach(company -> companies.add(companyMapper.createCompanyDTO(company)));
        request.setAttribute("companies", companies);
        request.setAttribute("computer", computer);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/editComputer.jsp");
        view.forward(request, response);
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
        if (request.getParameter("computerId") == null) {
            response.sendRedirect("/cdb/dashboard");
        } else {
            Long id = Long.parseLong(request.getParameter("computerId"));
            Optional<Computer> computerOpt = computerService.getComputer(id);
            if (computerOpt.isPresent()) {
                generateAndForwardDTOs(request, response, computerOpt);
            } else {
                response.sendRedirect("/cdb/dashboard");
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Optional<Long> idOpt = getLongParam(request, "id");
        if (idOpt.isPresent()) {
            Long id = idOpt.get();
            String name = request.getParameter("name");
            LocalDate introduced = getDate(request.getParameter("introduced"));
            LocalDate discontinued = getDate(request.getParameter("discontinued"));
            Optional<Long> companyID = getLongParam(request, "companyId");
            Company company = null;
            if (companyID.isPresent()) {
                company = new Company.Builder(companyID.get()).build();
            }
            Computer computer = new Computer.Builder(id).withName(name).withIntroduced(introduced)
                    .withDiscontinued(discontinued).withCompany(company).build();
            executeUpdate(request, computer);
        } else {
            request.setAttribute("error", "No such computer in database.");
        }
        doGet(request, response);
    }
}
