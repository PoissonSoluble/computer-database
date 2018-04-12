package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.IComputerDTOMapper;
import com.excilys.cdb.pagination.ComputerPage;
import com.excilys.cdb.service.IComputerService;
import com.excilys.cdb.service.ServiceException;

@WebServlet(name = "DashboardServlet", urlPatterns = "/dashboard")
public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = -3346293799223556529L;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final ComputerOrdering DEFAULT_ORDER = ComputerOrdering.CU_ID;
    private static final boolean DEFAULT_ASCENDING = true;

    @Autowired
    private IComputerService computerService;
    @Autowired
    private IComputerDTOMapper computerMapper;

    public DashboardServlet() {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNumber = getIntParam(request, "pageNumber", DEFAULT_PAGE);
        int pageSize = getIntParam(request, "pageSize", DEFAULT_SIZE);
        String search = request.getParameter("search");
        ComputerOrdering order = getOrderingParam(request, "order", DEFAULT_ORDER);
        boolean ascending = getBooleanParam(request, "ascending", DEFAULT_ASCENDING);
        ComputerPage page = new ComputerPage(pageNumber, pageSize, search, order, ascending, computerService);
        List<ComputerDTO> computerDTOs = getDTOsFromPage(page);
        setRequestAttributes(request, pageSize, search, page, computerDTOs, order, ascending);
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard.jsp");
        view.forward(request, response);
    }

    private boolean getBooleanParam(HttpServletRequest request, String paramName, boolean defaultValue) {
        String param = request.getParameter(paramName);
        if (param != null) {
            if (param.equalsIgnoreCase("true")) {
                return true;
            } else if (param.equalsIgnoreCase("false")) {
                return false;
            }
        }
        return defaultValue;
    }

    private List<ComputerDTO> getDTOsFromPage(ComputerPage page) {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        page.get().forEach(computer -> computerDTOs.add(computerMapper.createComputerDTO(computer)));
        return computerDTOs;
    }

    private int getIntParam(HttpServletRequest request, String param, int defaultValue) {
        try {
            return Integer.parseInt(request.getParameter(param));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private ComputerOrdering getOrderingParam(HttpServletRequest request, String paramName,
            ComputerOrdering defaultValue) {
        String orderString = request.getParameter(paramName);
        ComputerOrdering order;
        if (orderString == null) {
            order = defaultValue;
        } else {
            order = Stream.of(ComputerOrdering.values()).filter(v -> v.accept(orderString)).findFirst()
                    .orElse(defaultValue);
        }
        return order;
    }

    private void setRequestAttributes(HttpServletRequest request, int pageSize, String search, ComputerPage page,
            List<ComputerDTO> computerDtos, ComputerOrdering order, boolean ascending) {
        request.setAttribute("computers", computerDtos);
        request.setAttribute("pageNumber", page.getPageNumber());
        request.setAttribute("totalPage", page.getPageTotal());
        if (search == null) {
            search = "";
        }
        request.setAttribute("search", search);
        try {
            request.setAttribute("computerAmount", computerService.getComputerAmount(search));
        } catch (ServiceException e) {
            request.setAttribute("computerAmount", 0);
        }
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("order", order.toString());
        request.setAttribute("ascending", ascending);
    }
}
