package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.service.IComputerService;

@WebServlet(name = "DeleteComputerServlet", urlPatterns = "/deleteComputer")
public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    private IComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputerServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNumber;
        int pageSize;
        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (NumberFormatException e) {
            pageNumber = 1;
            pageSize = 10;

        }
        response.sendRedirect(new StringBuilder("/cdb/dashboard?pageNumber=").append(pageNumber).append("&pageSize=")
                .append(pageSize).toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selection = request.getParameter("selection");
        List<Long> ids = new ArrayList<>();
        for (String idString : selection.split(",")) {
            ids.add(Long.parseLong(idString));
        }
        computerService.deleteComputers(ids);
        doGet(request, response);
    }
}
