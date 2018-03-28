package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerService;

public class DeleteComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ComputerService service = ComputerService.INSTANCE;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        response.sendRedirect(new StringBuilder("/cdb/dashboard?pageNumber=").append(pageNumber).append("&pageSize=")
                .append(pageSize).toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String selection = request.getParameter("selection");
        List<Long> ids = new ArrayList<>();
        for (String idString : selection.split(",")) {
            ids.add(Long.parseLong(idString));
        }
        service.deleteComputers(ids);
        doGet(request, response);
    }
}
