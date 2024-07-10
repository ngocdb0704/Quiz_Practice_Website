/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package app.controller;

import app.dal.DAOQuiz;
import app.entity.QuizInformation;
import app.entity.QuizLevel;
import app.entity.QuizType;
import app.entity.Question;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Admin
 */
@WebServlet(name="AddQuizServlet", urlPatterns={"/admin/addquiz"})
public class AddQuizServlet extends HttpServlet {
   
    DAOQuiz quizDAO = new DAOQuiz();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String quizName = request.getParameter("name");
        int subjectId = Integer.parseInt(request.getParameter("subject"));
        int levelInt = Integer.parseInt(request.getParameter("examLevel"));
        int duration = Integer.parseInt(request.getParameter("duration"));
        int passRate = Integer.parseInt(request.getParameter("passRate"));
        int quizTypeInt = Integer.parseInt(request.getParameter("quizType"));
        String description = request.getParameter("description");
        int totalQuestions = Integer.parseInt(request.getParameter("totalQuestions"));
        String[] lessonIds = request.getParameterValues("lessonId");
        String[] questionCounts = request.getParameterValues("numberQuestion");
        
        QuizLevel level = QuizLevel.fromInt(levelInt);
        QuizType quizType = QuizType.fromInt(quizTypeInt);
        
        QuizInformation quiz = new QuizInformation();
        quiz.setQuizName(quizName);
        quiz.setSubjectId(subjectId);
        quiz.setLevel(level);
        quiz.setDurationInMinutes(duration);
        quiz.setPassRate(passRate);
        quiz.setType(quizType);
        quiz.setDescription(description);
        quiz.setTotalQuestion(totalQuestions);
        
        int quizId = quizDAO.addQuiz(quiz);

        // Handle adding questions to the quiz
        List<Question> allQuestions = quizDAO.getAllQuestionsBySubject(subjectId);
        Random rand = new Random();
        for (int i = 0; i < totalQuestions; i++) {
            Question question = allQuestions.get(rand.nextInt(allQuestions.size()));
            quizDAO.addQuestionToQuiz(quizId, question.getQuestionId());
        }

        // Handle adding quiz lesson question counts
        if (lessonIds != null && questionCounts != null) {
            for (int i = 0; i < lessonIds.length; i++) {
                int lessonId = Integer.parseInt(lessonIds[i]);
                int questionCount = Integer.parseInt(questionCounts[i]);
                quizDAO.addQuizLessonQuestionCount(quizId, lessonId, questionCount);
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/quizzeslist");
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
