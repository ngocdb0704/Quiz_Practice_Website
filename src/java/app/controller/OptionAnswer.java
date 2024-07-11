package app.controller;

import app.dal.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Hoapmhe173343
 */
@WebServlet(name = "OptionAnswer", urlPatterns = {"/admin/optionanswer"})
public class OptionAnswer extends HttpServlet {

    QuestionDAO quesDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAddOption(request, response);
        } else if ("delete".equals(action)) {
            handleDeleteOption(request, response);
        }
    }

    private void handleAddOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int questionID = Integer.parseInt(request.getParameter("questionID"));
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        
        boolean isHas = quesDAO.isQuestionInQuiz(questionID);
        if (isHas) {
            out.print("{\"message\": \"This question is already in the quiz, options cannot be added\", \"success\": false}");
            return;
        }

        String answer = request.getParameter("answerName");
        int isCorrect = Integer.parseInt(request.getParameter("isCorrect"));

        boolean isOptionExisted = quesDAO.isExistOptionAns(questionID, answer);

        if (isOptionExisted) {
            out.print("{\"message\": \"This option already exists\", \"success\": false}");
        } else {
            quesDAO.addAnswer(questionID, answer, isCorrect);
            out.print("{\"message\": \"Add options successfully\", \"success\": true}");
        }
        out.flush();

    }

    private void handleDeleteOption(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int answerID = Integer.parseInt(request.getParameter("answerID"));

        int questionID = Integer.parseInt(request.getParameter("questionID"));
        if (quesDAO.isQuestionInQuiz(questionID)) {
            response.getWriter().print("{\"status\":\"error\", \"message\":\"This question is already in the quiz, so it cannot delete option.\"}");
            response.getWriter().flush();
            return;
        }

        //number option answer of question
        int numberOfOptions = quesDAO.getNumberOfOptions(questionID);
        //number option true of question
        int numberOfCorrectOptions = quesDAO.getNumberOfCorrectOptions(questionID);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (numberOfOptions <= 2) {
                out.print("{\"message\": \"Cannot delete the option because there are only 2 options left!\", \"success\": false}");
            } else if (numberOfCorrectOptions <= 1 && quesDAO.isOptionCorrect(answerID)) {
                out.print("{\"message\": \"Cannot delete the only correct option!\", \"success\": false}");
            } else {
                boolean isDelete = quesDAO.deleteOption(answerID);
                if (isDelete) {
                    out.print("{\"message\": \"Option deleted successfully!\", \"success\": true}");
                } else {
                    out.print("{\"message\": \"Failed to delete the option!\", \"success\": false}");
                }
            }
            out.flush();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
