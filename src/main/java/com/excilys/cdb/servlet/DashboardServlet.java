package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.pagination.ComputerPage;

public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = -3346293799223556529L;
    private final int DEFAULT_PAGE = 1;
    private final int DEFAULT_SIZE = 10;

    public DashboardServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNumber = getIntParam(request, "pageNumber", DEFAULT_PAGE);
        int pageSize = getIntParam(request, "pageSize", DEFAULT_SIZE);

        ComputerPage page = new ComputerPage(pageNumber, pageSize);
        List<ComputerDTO> dtos = new ArrayList<>();
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        page.get().forEach(computer -> dtos.add(mapper.createComputerDTO(computer)));
        
        request.setAttribute("computers", dtos);
        request.setAttribute("pageNumber", page.getPageNumber());
        request.setAttribute("totalPage", page.getPageTotal());
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/dashboard.jsp");
        view.forward(request, response);
    }

    private int getIntParam(HttpServletRequest request, String param, int defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(param));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
