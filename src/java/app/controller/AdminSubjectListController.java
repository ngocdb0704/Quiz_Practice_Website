
package app.controller;

import app.dal.DAOSubjectAdmin;
import app.dal.QueryResult;
import app.utils.Config;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="AdminSubjectListController", urlPatterns={"/admin/subjectlist"})
public class AdminSubjectListController extends HttpServlet {
    private final static String SUBJECT_LIST_PAGE = "/admin/subjectlist/SubjectList.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Config cfg = new Config(getServletContext());

        int page = Parsers.parseIntOrDefault(request.getParameter("page"), 1);
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);
        int asc = Parsers.parseIntOrDefault(request.getParameter("asc"), 1);
        String titleQuery = request.getParameter("title");
        
        DAOSubjectAdmin dsa = new DAOSubjectAdmin();
        QueryResult result = dsa.search(titleQuery, asc == 1 ? true : false, page, pageSize);
        request.setAttribute("result", result);
        
        request.getRequestDispatcher(SUBJECT_LIST_PAGE).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher(SUBJECT_LIST_PAGE).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "AdminSubjectListController Servlet";
    }
}
