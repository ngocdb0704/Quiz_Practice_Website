
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
@WebServlet(name="OptionAnswer", urlPatterns={"/admin/optionanswer"})
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
        String answer = request.getParameter("answerName");
        int isCorrect = Integer.parseInt(request.getParameter("isCorrect"));

        boolean isOptionExisted = quesDAO.isExistOptionAns(questionID, answer);
        if (isOptionExisted) {
            request.setAttribute("notification", "This option already exists");
        } else {
            quesDAO.addAnswer(questionID, answer, isCorrect);
            request.setAttribute("notification", "Add options successfully");
        }

        response.sendRedirect(request.getContextPath() + "/admin/questionlist/details?qid=" + questionID);
    }

    private void handleDeleteOption(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        int questionID = Integer.parseInt(request.getParameter("questionID"));
        int answerID = Integer.parseInt(request.getParameter("answerID"));
        boolean isDelete = quesDAO.deleteAnswer(answerID);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (isDelete) {
                out.print("{\"message\": \"Option deleted successfully!\", \"success\": true}");
            } else {
                out.print("{\"message\": \"Failed to delete the option!\", \"success\": false}");
            }
            out.flush();
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
