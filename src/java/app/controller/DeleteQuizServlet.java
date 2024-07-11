package app.controller;

import app.dal.DAOQuiz;
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
@WebServlet(name="DeleteQuizServlet", urlPatterns={"/admin/deletequiz"})
public class DeleteQuizServlet extends HttpServlet {
   
    DAOQuiz quizDAO = new DAOQuiz();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 
     

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            if (quizDAO.isQuizAttempted(quizId)) {
                out.write("{\"status\":\"error\", \"message\":\"attempted\"}");
            } else if (quizDAO.deleteQuiz(quizId)) {
                out.write("{\"status\":\"success\"}");
            } else {
                out.write("{\"status\":\"error\", \"message\":\"Failed to delete quiz.\"}");
            }
        } catch (Exception e) {
            out.write("{\"status\":\"error\", \"message\":\"An error occurred.\"}");
        } finally {
            out.flush();
            out.close();
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
