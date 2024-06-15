package app.controller;

import app.dal.DAOQuiz;
import app.dal.QueryResult;
import app.entity.QuizType;
import app.utils.Config;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

        String published = request.getParameter("published");
        if (published == null) {
            response.sendRedirect("?published=1");
        }
        
        String type = request.getParameter("quizTypes");
        String subject = request.getParameter("subjectIds");

        boolean isPublished = Parsers.parseIntOrDefault(published, 1) == 1 ? true : false;
        QuizType quizType = QuizType.fromInt(Parsers.parseIntOrDefault(type, -1));

        QueryResult result = daoQuiz.search(
                request.getParameter("quizName"),
                isPublished,
                quizType,
                page, pageSize
        );

        request.setAttribute("result", result);

        request.getRequestDispatcher(PAGE_NAME).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String[] values = request.getParameterValues("ids[]");
        String action = request.getParameter("action");
        Integer[] ids = Parsers.parseInts(values);
        DAOQuiz daoQuiz = new DAOQuiz();

        if (ids.length > 0) {
            switch (action) {
                case "markDraft" -> daoQuiz.markDraft(ids);
                case "publish" -> daoQuiz.publish(ids);
                default -> System.out.println(action);
            }
        }

        String redirectUrl = String.format(
                "quizzeslist?published=%s&subjectIds=%s&quizTypes=%s",
                request.getParameter("published"),
                request.getParameter("subjectIds"),
                request.getParameter("quizTypes")
        );

        String quizName = request.getParameter("quizName");
        if (quizName != null) {
            redirectUrl += "&quizName=" + quizName;
        }

        String page = request.getParameter("page");
        if (page != null) {
            redirectUrl += "&page=" + page;
        }

        response.sendRedirect(redirectUrl);
    }

    @Override
    public String getServletInfo() {
        return "QuizzesListController Servlet";
    }
}
