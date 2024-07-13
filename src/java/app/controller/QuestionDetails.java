/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.QuestionDAO;
import app.entity.Question;
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
@WebServlet(name = "QuestionDetails", urlPatterns = {"/admin/questionlist/details"})
public class QuestionDetails extends HttpServlet {

    QuestionDAO quesDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("qid");
        if (id == null) {
            response.sendRedirect("../questionlist");
            return;
        }

        int questionId;
        try {
            questionId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            response.sendRedirect("../questionlist");
            return;
        }

        Question question = quesDAO.getQuestion(questionId);
        request.setAttribute("question", question);
        
        
        request.getRequestDispatcher("../questiondetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
