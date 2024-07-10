/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
 * @author Admin
 */
@WebServlet(name = "DeleteQuestionServet", urlPatterns = {"/admin/deletequestion"})
public class DeleteQuestionServet extends HttpServlet {

    QuestionDAO quesDao = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String questionIdParam = request.getParameter("questionID");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            int questionId = Integer.parseInt(questionIdParam);

            try {
                if (quesDao.isQuestionInQuiz(questionId)) {
                    out.print("{\"status\":\"error\", "
                            + "\"message\":\"This question is already in the quiz, so it cannot be deleted\"}");
                } else {
                    quesDao.deleteAnswer(questionId);   
                    quesDao.deleteQuestion(questionId);
                    out.print("{\"status\":\"success\", "
                            + "\"message\":\"Question deleted successfully.\"}");
                }
            } catch (Exception e) {
                out.print("{\"status\":\"error\", "
                        + "\"message\":\"An error occurred while deleting the question.\"}");
            }
        } catch (NumberFormatException e) {
            out.print("{\"status\":\"error\", "
                    + "\"message\":\"Invalid question ID.\"}");
        }

        out.flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
