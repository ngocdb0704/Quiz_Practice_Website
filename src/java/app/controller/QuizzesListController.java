package app.controller;

import app.dal.DAOQuiz;
import app.dal.QueryResult;
import app.entity.QuizType;
import app.entity.Subject;
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
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name="QuizzesListController", urlPatterns={"/admin/quizzeslist"})
public class QuizzesListController extends HttpServlet {
    private final static String PAGE_NAME = "/admin/quizzeslist/QuizzesList.jsp";

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
        
        String subject = request.getParameter("subjectIds");
        String quizName = request.getParameter("quizName");
        String quizTypes = request.getParameter("quizTypes");
        
        int type = Parsers.parseIntOrDefault(quizTypes, -1);
        int subjectId = Parsers.parseIntOrDefault(subject, -1);
        boolean isPublished = Parsers.parseIntOrDefault(published, 1) == 1 ? true : false;
        QuizType quizType = QuizType.fromInt(type);

        QueryResult result = daoQuiz.search(
                quizName,
                isPublished,
                subjectId,
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
        
        List<Subject> subjects = daoQuiz.getSubjectsWithQuiz();

        request.setAttribute("subjects", subjects);
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
            HttpSession session = request.getSession(true);
            
            switch (action) {
                case "markDraft" -> {
                    int n = daoQuiz.markDraft(ids);
                    
                    if (n == ids.length) {
                        session.setAttribute("notyfSuccessMessage", "Archived " + n + " items");
                    } else {
                        session.setAttribute("notyfErrorMessage", "Some of your items cannot be archived");
                    }
                }
                case "publish" -> {
                    int n = daoQuiz.publish(ids);
                    
                    if (n == ids.length) {
                        session.setAttribute("notyfSuccessMessage", "Published " + n + " items");
                    } else {
                        session.setAttribute("notyfErrorMessage", "Some of your items cannot be published");
                    }
                }
                case "delete" -> {
                    int n = daoQuiz.delete(ids);

                    if (n == ids.length) {
                        session.setAttribute("notyfSuccessMessage", "Deleted " + n + " items");
                    } else {
                        session.setAttribute("notyfErrorMessage", "Some of your items cannot be deleted");
                    }
                }
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
