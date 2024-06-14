
package app.controller;

import app.dal.DAOQuiz;
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

@WebServlet(name="QuizzesListController", urlPatterns={"/admin/quizzeslist"})
public class QuizzesListController extends HttpServlet {
    private final static String PAGE_NAME = "/admin/quizzes/QuizzesList.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Config cfg = new Config(getServletContext());

        int page = Parsers.parseIntOrDefault(request.getParameter("page"), 1);
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);
        DAOQuiz daoQuiz = new DAOQuiz();

        boolean published = 
                Parsers.parseIntOrDefault(request.getParameter("published"), 1) == 1 ? true : false;

        QueryResult result = daoQuiz.search(
                request.getParameter("quizName"),
                published,
                page, pageSize
        );
        request.setAttribute("result", result);

        request.getRequestDispatcher(PAGE_NAME).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher(PAGE_NAME).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "QuizzesListController Servlet";
    }
}
