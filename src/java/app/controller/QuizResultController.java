
package app.controller;

import app.dal.DAOAttempt;
import app.dal.DAOQuiz;
import app.entity.Attempt;
import app.entity.QuizInformation;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="QuizResultController", urlPatterns={"/user/quizresult"})
public class QuizResultController extends HttpServlet {
    private static final String QUIZ_RESULT = "/user/quizresult/QuizResult.jsp";

    private String getHomePage() {
        return getServletContext().getContextPath();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String attemptIdRaw = request.getParameter("attemptId");
        int attemptId = Parsers.parseIntOrDefault(attemptIdRaw, -1);

        if (attemptId == -1) {
            response.sendRedirect(getHomePage());
            return;
        }
        
        DAOAttempt dat = new DAOAttempt();
        DAOQuiz dquiz = new DAOQuiz();
        Attempt attempt = dat.getAttemptById(attemptId);
        QuizInformation quiz = dquiz.getQuizById(attempt.getQuizId());

        int total = dat.getAllAttemptsWithoutQuestion(attemptId).size();
        int min = (int)Math.ceil(((double)quiz.getPassRate() / 100) * total);

        request.setAttribute("min", min);
        request.setAttribute("total", total);
        request.setAttribute("pass", attempt.getCorrectCount() >= min);
        request.setAttribute("attempt", attempt);
        request.getRequestDispatcher(QUIZ_RESULT).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher(QUIZ_RESULT).forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "QuizResultController Servlet";
    }
}
