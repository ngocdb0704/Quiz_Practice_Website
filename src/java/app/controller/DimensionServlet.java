/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

/**
 *
 * @author OwO
 */

import app.dal.DAODimension;
import app.entity.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DimensionServlet")
public class DimensionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DAODimension daoDimension = new DAODimension();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            showAddForm(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else if ("delete".equals(action)) {
            deleteDimension(request, response);
        } else {
            listDimensions(request, response);
        }
    }

    private void listDimensions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        try {
            List<Dimension> listDimension = daoDimension.listAllDimensions(subjectId);
            request.setAttribute("dimensions", listDimension);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/subjectdetail/dimension/Dimension.jsp?subjectId=" + subjectId);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        request.setAttribute("subjectId", subjectId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/subjectdetail/dimension/DimensionAdd.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addDimension(request, response);
        } else if ("update".equals(action)) {
            updateDimension(request, response);
        } else if ("delete".equals(action)) {
            deleteDimension(request, response);
        } else {
            listDimensions(request, response);
        }
    }

    private void addDimension(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String dimensionType = request.getParameter("dimensionType");
        String dimensionName = request.getParameter("dimensionName");
        String description = request.getParameter("description");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));

        Dimension newDimension = new Dimension(dimensionType, dimensionName, description, subjectId);

        try {
            daoDimension.insertDimension(newDimension);
            response.sendRedirect("DimensionServlet?subjectId=" + subjectId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        try {
            Dimension existingDimension = daoDimension.getDimension(dimensionId);
            request.setAttribute("dimension", existingDimension);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/subjectdetail/dimension/DimensionUpdate.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void updateDimension(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
        String dimensionType = request.getParameter("dimensionType");
        String dimensionName = request.getParameter("dimensionName");
        String description = request.getParameter("description");
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));

        Dimension dimension = new Dimension(dimensionId, dimensionType, dimensionName, description, subjectId);
        try {
            daoDimension.updateDimension(dimension);
            response.sendRedirect("DimensionServlet?subjectId=" + subjectId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void deleteDimension(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int dimensionId = Integer.parseInt(request.getParameter("dimensionId"));
        int subjectId = Integer.parseInt(request.getParameter("subjectId"));
        try {
            daoDimension.deleteDimension(dimensionId);
            response.sendRedirect("DimensionServlet?subjectId=" + subjectId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
