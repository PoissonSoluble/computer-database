package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.pagination.ComputerPage;

public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = -3346293799223556529L;
    
    public DashboardServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("computer", new ComputerPage(1, 10));
        RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
        view.forward(request, response);  
    }
}
