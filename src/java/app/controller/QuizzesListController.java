package app.controller;

import app.dal.DAOQuiz;
import app.dal.QueryResult;
import app.entity.QuizType;
import app.utils.Config;
import app.utils.Parsers;
import app.utils.URLUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            return;
        }
        
        int type = Parsers.parseIntOrDefault(request.getParameter("quizTypes"), -1);
        String subject = request.getParameter("subjectIds");
        String quizName = request.getParameter("quizName");

        boolean isPublished = Parsers.parseIntOrDefault(published, 1) == 1 ? true : false;
        QuizType quizType = QuizType.fromInt(type);

        QueryResult result = daoQuiz.search(
                quizName,
                isPublished,
                quizType,
                page, pageSize
        );

        boolean isSearching = (quizName != null && !quizName.isBlank()) || type != -1;
        request.setAttribute("isSearching", isSearching);

        if (result.getTotalPages() > 0 && page > result.getTotalPages()) {
            String params = request.getQueryString();
            params = params.replace("page=" + page, "page=" + result.getTotalPages());
            response.sendRedirect("quizzeslist?" + params);
        }

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
                case "delete" -> daoQuiz.delete(ids);
                default -> System.out.println(action);
            }
        }

        String redirectUrl = String.format(
                "quizzeslist?published=%s",
                request.getParameter("published")
        );

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
