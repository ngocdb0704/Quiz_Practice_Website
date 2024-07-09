
package app.controller;

import app.dal.DAOAttempt;
import app.dal.DAOUser;
import app.dal.QueryResult;
import app.entity.Attempt;
import app.entity.User;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="QuizHandleController", urlPatterns={"/user/quizhandle"})
public class QuizHandleController extends HttpServlet {
    private static final String PAGE_NAME = "/user/quizhandle/QuizHandle.jsp";

    private String getCurrentUserEmail(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) return null;

        Object mailAttr = session.getAttribute("userEmail");
        if (mailAttr == null) return null;

        return mailAttr.toString();
    }

    private String getHomePage() {
        return getServletContext().getContextPath();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String attemptIdRaw = request.getParameter("attemptId");
        String qRaw = request.getParameter("q");

        int q = Parsers.parseIntOrDefault(qRaw, 1);
        int attemptId = Parsers.parseIntOrDefault(attemptIdRaw, -1);

        if (attemptId == -1) {
            response.sendRedirect(getHomePage());
            return;
        }

        /*
        String email = getCurrentUserEmail(request);
        if (email == null) {
            response.sendRedirect(getHomePage());
            return;
        }

        DAOUser du = new DAOUser();
        User user = du.getUserByEmail(email);


        if (attempt.getUserId() != user.getUserId()) {
            response.sendRedirect(getHomePage());
            return;
        }
        */

        DAOAttempt dat = new DAOAttempt();
        Attempt attempt = dat.getAttemptById(attemptId);

        if (attempt.isFinished()) {
            request.getRequestDispatcher("/user/quizhandle/QuizExpired.jsp").forward(request, response);
            return;
        }
        
        QueryResult result = dat.paginateAttemptQuestions(attemptId, q);

        request.setAttribute("attempt", attempt);
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
        return "QuizHandleController Servlet";
    }
}
