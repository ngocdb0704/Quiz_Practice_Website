package app.controller;

import app.dal.DAOQuiz;
import app.dal.DAOSubject;
import app.entity.QuizInformation;
import app.entity.QuizLevel;
import app.entity.QuizType;
import app.entity.Question;
import app.entity.QuizLesson;
import app.entity.Subject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddQuizServlet", urlPatterns = {"/admin/addquiz"})
public class AddQuizServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AddQuizServlet.class.getName());

    DAOQuiz quizDAO = new DAOQuiz();
    DAOSubject subDao = new DAOSubject();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Subject> listSubjects = subDao.getAllSubject();
        Map<Integer, String> subjectMap = new HashMap<>();
        for (Subject subject : listSubjects) {
            subjectMap.put(subject.getSubjectId(), subject.getSubjectName());
        }

        List<Integer> lessonList = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            lessonList.add(i);
        }

        request.setAttribute("lessonList", lessonList);
        request.setAttribute("subjectMap", subjectMap);
        request.getRequestDispatcher("addQuiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quizName = request.getParameter("name");
        int subjectId = Integer.parseInt(request.getParameter("subject"));
        int levelInt = Integer.parseInt(request.getParameter("examLevel"));
        String durationStr = request.getParameter("duration");
        String passRateStr = request.getParameter("passRate");
        int quizTypeInt = Integer.parseInt(request.getParameter("quizType"));
        String description = request.getParameter("description");
        String totalQuestionsStr = request.getParameter("totalQuestions");

        String[] lessonIds = request.getParameterValues("lessonId");
        String[] questionCounts = request.getParameterValues("numberQuestion");

        // Add these attributes to keep the input data
        request.setAttribute("quizName", quizName);
        request.setAttribute("subjectId", subjectId);
        request.setAttribute("levelInt", levelInt);
        request.setAttribute("duration", durationStr);
        request.setAttribute("passRate", passRateStr);
        request.setAttribute("quizTypeInt", quizTypeInt);
        request.setAttribute("description", description);
        request.setAttribute("totalQuestions", totalQuestionsStr);
        request.setAttribute("lessonIds", lessonIds);
        request.setAttribute("questionCounts", questionCounts);

        List<QuizLesson> lessonQuestions = new ArrayList<>();
        for (int i = 0; i < lessonIds.length; i++) {
            lessonQuestions.add(new QuizLesson(-1, Integer.parseInt(lessonIds[i]), Integer.parseInt(questionCounts[i])));
        }
        request.setAttribute("lessonQuestions", lessonQuestions);

        if (quizName.trim().isEmpty()) {
            request.setAttribute("errorName", "Quiz name cannot be empty!");
            doGet(request, response);
            return;
        }

        if (lessonIds == null) {
            request.setAttribute("errorLesson", "Please select the question component in the quiz!");
            doGet(request, response);
            return;
        }

        for (String id : lessonIds) {
            if (!id.trim().matches("\\d+")) {
                request.setAttribute("errorMessage", "Lesson IDs must be numbers.");
                doGet(request, response);
                return;
            }
        }

        for (String count : questionCounts) {
            if (!count.trim().matches("\\d+")) {
                request.setAttribute("errorMessage", "Number of questions must be numbers.");
                doGet(request, response);
                return;
            }
        }

        int duration = 0;
        int passRate = 0;
        int totalQuestions = 0;

        try {
            duration = Integer.parseInt(durationStr);
            passRate = Integer.parseInt(passRateStr);
            totalQuestions = Integer.parseInt(totalQuestionsStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Duration, Pass Rate, and Total Questions must be valid numbers.");
            doGet(request, response);
            return;
        }

        // Check duration
        if (duration <= 0) {
            request.setAttribute("errorDuration", "Duration must be greater than 0");
            doGet(request, response);
            return;
        }

        // Check pass rate
        if (passRate <= 0 || passRate > 100) {
            request.setAttribute("errorPassRate", "Pass rate must be between 1 and 100");
            doGet(request, response);
            return;
        }

        // Calculate the sum of questionCounts
        int sumOfQuestionCounts = 0;
        for (String count : questionCounts) {
            sumOfQuestionCounts += Integer.parseInt(count.trim());
        }

        // Check totalQuestions equals sum of questionCounts
        if (totalQuestions != sumOfQuestionCounts) {
            request.setAttribute("errorTotalQuestion", "Total questions must equal the sum of question counts.");
            doGet(request, response);
            return;
        }

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

        int quizId = -1;
        try {
            quizId = quizDAO.addQuiz(quiz);
            if (quizId == -1) {
                throw new Exception("Failed to insert quiz.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding quiz", e);
            request.setAttribute("errorMessage", "Error adding quiz. Please try again.");
            doGet(request, response);
            return;
        }

        LOGGER.log(Level.INFO, "Added quiz with ID: {0}", quizId);

        try {
            // Process lessons and questions for each lesson
            for (int i = 0; i < lessonIds.length; i++) {
                int lessonId = Integer.parseInt(lessonIds[i].trim());
                int questionCount = Integer.parseInt(questionCounts[i].trim());

                List<Question> questionByLesson = quizDAO.getQuestionsByLessonId(lessonId);

                if (questionByLesson.size() < questionCount) {
                    request.setAttribute("errorMessage", "Not enough questions in lesson ID: " + lessonId);
                    doGet(request, response);
                    return;
                }

                List<Question> selectedQuestions = new ArrayList<>();
                Random rand = new Random();
                while (selectedQuestions.size() < questionCount) {
                    Question question = questionByLesson.get(rand.nextInt(questionByLesson.size()));
                    if (!selectedQuestions.contains(question)) {
                        selectedQuestions.add(question);
                    }
                }

                for (Question question : selectedQuestions) {
                    quizDAO.addQuestionToQuiz(quizId, question.getQuestionID());
                    LOGGER.log(Level.INFO, "Added question with ID: {0} to quiz with ID: {1}", new Object[]{question.getQuestionID(), quizId});
                }

                quizDAO.addQuizLessonQuestionCount(quizId, lessonId, questionCount);
                LOGGER.log(Level.INFO, "Added lesson ID: {0} with question count: {1} to quiz with ID: {2}", new Object[]{lessonId, questionCount, quizId});
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding questions or lesson question counts to quiz", e);
            request.setAttribute("errorMessage", "Error adding questions or lesson question counts to quiz. Please try again.");
            doGet(request, response);
            return;
        }

        request.setAttribute("successMessage", "Add quiz successfully.");
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
