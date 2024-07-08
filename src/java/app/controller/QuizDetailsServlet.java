/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.dal.DAOQuiz;
import app.entity.QuizInformation;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author hoapmhe173343
 */
@WebServlet(name = "QuizDetailsServlet", urlPatterns = {"/admin/quizzeslist/details"})
public class QuizDetailsServlet extends HttpServlet {
    DAOQuiz quizDao = new DAOQuiz();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quizIdRaw = request.getParameter("quizid");
        if(quizIdRaw == null ){
            response.sendRedirect("../quizzeslist");
            return;
        }
        
        int quizId;
        try{
            quizId = Integer.parseInt(quizIdRaw);
        }catch(NumberFormatException e){
            response.sendRedirect("../quizzeslist");
            return;
        }
        
        QuizInformation quiz = quizDao.getQuizById(quizId);
        request.setAttribute("quiz", quiz);
        
        request.getRequestDispatcher("../quizdetails.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
    }
    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
