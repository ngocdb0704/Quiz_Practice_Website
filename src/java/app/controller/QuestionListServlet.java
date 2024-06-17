package app.controller;

import app.dal.DAOSubject;
import app.dal.QueryResult;
import app.dal.QuestionDAO;
import app.entity.Answer;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import app.entity.Question;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import app.entity.Subject;

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
        String subjectIdParam = request.getParameter("subjectId");
        String levelParam = request.getParameter("level");
        String statusParam = request.getParameter("status");
        String content = request.getParameter("searchContent");

        int subjectId = subjectIdParam != null ? Integer.parseInt(subjectIdParam) : 0;
        int level = levelParam != null ? Integer.parseInt(levelParam) : 0;
        int status = statusParam != null ? Integer.parseInt(statusParam) : 0;

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

        QueryResult<Question> result = quesDao.filters(subjectId, level, status, content, page, 10);
        request.setAttribute("totalPage", result.getTotalPages());
        request.setAttribute("currentPage", page);
        request.setAttribute("listQuestion", result.getResults());

        for (Question q : result.getResults()) {
            System.out.println(q.getQuestionName());
        }

        List<Subject> listSubjects = subDao.getAllSubject();
        Map<Integer, String> subjectMap = new HashMap<>();
        for (Subject subject : listSubjects) {
            subjectMap.put(subject.getSubjectId(), subject.getSubjectName());
        }

        Map<Integer, String> levelMap = new HashMap<>();
        levelMap.put(1, "Easy");
        levelMap.put(2, "Medium");
        levelMap.put(3, "Hard");

        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(0, "Hide");
        statusMap.put(1, "Show");

        request.setAttribute("subjectMap", subjectMap);
        request.setAttribute("levelMap", levelMap);
        request.setAttribute("statusMap", statusMap);

        request.getRequestDispatcher("/admin/questionlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int questionID = Integer.parseInt(request.getParameter("questionID"));
        int status = Integer.parseInt(request.getParameter("status"));
        System.out.println("id " + questionID + "status " + status);
        //check update status question
        boolean result = quesDao.setStatus(questionID, status);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            if (result) {
                out.print("{\"message\": \"Update status success!\", \"success\": true}");
            } else {
                out.print("{\"message\": \"Update status fail!\", \"success\": false}");
            }
            out.flush();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
