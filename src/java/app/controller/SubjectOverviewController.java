
package app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="SubjectOverviewController", urlPatterns={"/admin/subjectdetail/overview"})
public class SubjectOverviewController extends HttpServlet {
    private final static String OVERVIEW_PAGE = "/admin/subjectdetail/overview/Overview.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher(OVERVIEW_PAGE).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher(OVERVIEW_PAGE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "PricePackageController Servlet";
    }
}
