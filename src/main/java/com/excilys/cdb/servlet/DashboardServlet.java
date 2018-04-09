package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dao.ComputerOrdering;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.pagination.ComputerPage;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.service.ServiceException;

public class DashboardServlet extends HttpServlet {

    private static final long serialVersionUID = -3346293799223556529L;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_SIZE = 10;
    private static final ComputerOrdering DEFAULT_ORDER = ComputerOrdering.CU_ID;
    private static final boolean DEFAULT_ASCENDING = true;

    public DashboardServlet() {
        super();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNumber = getIntParam(request, "pageNumber", DEFAULT_PAGE);
        int pageSize = getIntParam(request, "pageSize", DEFAULT_SIZE);
        String search = request.getParameter("search");
        ComputerOrdering order = getOrderingParam(request, "order", DEFAULT_ORDER);
        boolean ascending = getBooleanParam(request, "ascending", DEFAULT_ASCENDING);

        ComputerPage page = new ComputerPage(pageNumber, pageSize, search, order, ascending);
        List<ComputerDTO> dtos = new ArrayList<>();
        getDTOs(page, dtos);

        setRequestAttributes(request, pageSize, search, page, dtos, order, ascending);
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

    private void getDTOs(ComputerPage page, List<ComputerDTO> dtos) {
        ComputerDTOMapper mapper = new ComputerDTOMapper();
        page.get().forEach(computer -> dtos.add(mapper.createComputerDTO(computer)));
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
            List<ComputerDTO> dtos, ComputerOrdering order, boolean ascending) {
        request.setAttribute("computers", dtos);
        request.setAttribute("pageNumber", page.getPageNumber());
        request.setAttribute("totalPage", page.getPageTotal());
        if (search == null) {
            search = "";
        }
        request.setAttribute("search", search);
        try {
            request.setAttribute("computerAmount", ComputerService.INSTANCE.getComputerAmount(search));
        } catch (ServiceException e) {
            request.setAttribute("computerAmount", 0);
        }
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("order", order.toString());
        request.setAttribute("ascending", ascending);
    }
}
