package app.controller;

import app.dal.QuestionDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import app.entity.Question;
import app.entity.Answer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SaveChangeQuestion", urlPatterns = {"/admin/savechange"})
public class SaveChangeQuestion extends HttpServlet {

    QuestionDAO quesDAO = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int questionID = Integer.parseInt(request.getParameter("questionID"));

        if (quesDAO.isQuestionInQuiz(questionID)) {
            response.getWriter().print("{\"status\":\"error\", \"message\":\"This question is already in the quiz, so it cannot be edit.\"}");
            response.getWriter().flush();
            return;
        }

        String questionName = request.getParameter("questionName");
        int subjectID = Integer.parseInt(request.getParameter("subjectId"));
        int lessonID = Integer.parseInt(request.getParameter("lessonID"));
        int level = Integer.parseInt(request.getParameter("level"));
        String explanation = request.getParameter("explanation");
        int status = Integer.parseInt(request.getParameter("status"));

        Question question = new Question(questionID, questionName, explanation, level, subjectID, lessonID, status, null);
        quesDAO.updateQuestion(question);

        // update answer
        List<Answer> answers = new ArrayList<>();
        String[] answerIDs = request.getParameterValues("answerID");
        String[] answerNames = request.getParameterValues("answerName");
        String[] isCorrects = request.getParameterValues("isCorrect");

        boolean hasCorrectAnswer = false;
        for (String isCorrectStr : isCorrects) {
            if (Integer.parseInt(isCorrectStr) == 1) {
                hasCorrectAnswer = true;
                break;
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (!hasCorrectAnswer) {
            response.getWriter().print("{\"status\":\"error\", \"message\":\"At least one answer must be correct.\"}");
            response.getWriter().flush();
            return;
        }

        for (int i = 0; i < answerIDs.length; i++) {
            int answerID = Integer.parseInt(answerIDs[i]);
            String answerName = answerNames[i];
            int isCorrect = Integer.parseInt(isCorrects[i]);

            Answer answer = new Answer(answerID, questionID, answerName, isCorrect);
            answers.add(answer);
            quesDAO.updateAnswer(answer);
        }

        question.setAnswers(answers);

        response.getWriter().print("{\"status\":\"success\"}");
        response.getWriter().flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
