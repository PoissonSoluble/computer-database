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
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.pagination.ComputerPage;

public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = -3346293799223556529L;
    
    public DashboardServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Computer> computers = new ComputerPage(2, 10).get();
        List<ComputerDTO> dtos = new ArrayList<>();
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        computers.forEach(computer -> dtos.add(mapper.createComputerDTO(computer)));
        for(ComputerDTO dto : dtos) {
            System.out.println(dto.getName());
        }
        request.setAttribute("computers", dtos);
        RequestDispatcher view = request.getRequestDispatcher("dashboard.jsp");
        view.forward(request, response);  
    }
}
