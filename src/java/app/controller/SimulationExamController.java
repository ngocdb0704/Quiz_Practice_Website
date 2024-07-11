
package app.controller;

import app.dal.DAOAttempt;
import app.dal.DAOQuiz;
import app.dal.DAOUser;
import app.dal.QueryResult;
import app.entity.Attempt;
import app.entity.User;
import app.utils.Config;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;

@WebServlet(name="SimulationExamController", urlPatterns={"/user/simulation"})
public class SimulationExamController extends HttpServlet {
    private static final String SIMULATION_LIST = "/user/simulation/SimulationExams.jsp";

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
        String userEmail = getCurrentUserEmail(request);

        if (userEmail == null) {
            response.sendRedirect(getHomePage());
            return;
        }

        Config cfg = new Config(getServletContext());

        int page = Parsers.parseIntOrDefault(request.getParameter("page"), 1);
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);

        String subjectIdRaw = request.getParameter("subjectId");
        String examName = request.getParameter("examName");

        int subjectId = Parsers.parseIntOrDefault(subjectIdRaw, -1);

        DAOQuiz dao = new DAOQuiz();

        QueryResult result = dao.getSimulationExams(subjectId, examName, userEmail, page, pageSize);
        request.setAttribute("result", result);
        request.setAttribute("subjects", dao.getSubjectsWithSimulationExams(userEmail));

        request.getRequestDispatcher(SIMULATION_LIST).forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String userEmail = getCurrentUserEmail(request);

        if (userEmail == null) {
            response.sendRedirect(getHomePage());
            return;
        }

        String action = request.getParameter("action");
        String quizRaw = request.getParameter("quiz");
        int quiz = Parsers.parseIntOrDefault(quizRaw, -1);
        DAOUser dUser = new DAOUser();
        DAOAttempt dat = new DAOAttempt();

        switch (action) {
            case "take" -> {
                if (quiz != -1) {
                    User user = dUser.getUserByEmail(userEmail);

                    if (user == null) {
                        break;
                    }

                    Attempt attempt = dat.createAttempt(quiz, user.getUserId());

                    if (attempt == null) {
                        break;
                    }

                    response.sendRedirect("quizhandle?attemptId=" + attempt.getAttemptId() + "&q=1");
                }
            }
            default -> {
                request.getRequestDispatcher(SIMULATION_LIST).forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "SimulationExamController Servlet";
    }
}
