package app.controller;

import app.dal.DAOSubject;
import app.dal.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import app.entity.Question;
import java.util.List;

/**
 *
 * @author hoapmhe173343
 */

public class QuestionListServlet extends HttpServlet {
    QuestionDAO quesDao = new QuestionDAO();
    DAOSubject subDao = new DAOSubject();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int record = 10;
        String pageString = request.getParameter("page");
        int page;
        try {
            page = Integer.parseInt(pageString);
            if (page <= 0) {
                page = 1;
            }
        } catch (NumberFormatException e) {
            page = 1;
        }
        List<Question> listQuestion = quesDao.questionPerPage(record, page);
        List<String> listSubjectTitle = subDao.getAllSubjectTitle();
        
        int totalQuestion = subDao.countQuestion();
        int totalPage = (int) Math.ceil((double) totalQuestion / record);
        System.out.println("tt question " + totalQuestion);
        System.out.println("tt page" + totalPage);
        request.setAttribute("totalPage", totalPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("listQuestion", listQuestion);
        request.setAttribute("listSubjectTitle", listSubjectTitle);
        
        request.getRequestDispatcher("/admin/questionlist.jsp").forward(request, response);
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
