package app.dal;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import app.entity.Question;
import app.entity.Answer;
/**
 *
 * @author hoapmhe173343
 */
public class QuestionDAO extends DBContext {

    public int addQuestion(String text, String explanation, int level, int subjectID, int lessonID) {
        String sql = "INSERT INTO [dbo].[Question] "
                + "([QuestionText], [Explanation], [Level], [SubjectID], [LessonID],[Status]) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, text);
            ps.setString(2, explanation);
            ps.setInt(3, level);
            ps.setInt(4, subjectID);
            ps.setInt(5, lessonID);
            ps.setInt(6, 1);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting question failed, no rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return generated question ID
                } else {
                    throw new SQLException("Inserting question failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Return -1 if insert fails
    }

    public void addAnswer(int questionID, String answerName, int isCorrect) {
        String sql = "INSERT INTO [dbo].[Answer]\n"
                + "           ([QuestionID]\n"
                + "           ,[AnswerName]\n"
                + "           ,[IsCorrect])\n"
                + "     VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionID);
            ps.setString(2, answerName);
            ps.setInt(3, isCorrect);
            ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        QuestionDAO dao = new QuestionDAO();
        List<Question> list = dao.questionPerPage(10, 2);
        for (Question question : list) {
            System.out.println(question);
        }
    }

    public List<Question> questionPerPage(int record, int page) {
        String sql = "WITH PaginatedQuestions AS (\n"
            + "    SELECT *\n"
            + "    FROM Question\n"
            + "    ORDER BY QuestionID\n"
            + "    OFFSET ? ROWS\n"
            + "    FETCH NEXT ? ROWS ONLY\n"
            + ")\n"
            + "SELECT pq.QuestionID, pq.QuestionText, pq.Explanation, pq.Level, pq.SubjectID, pq.LessonID, pq.Status, \n"
            + "       a.AnswerID, a.AnswerName, a.IsCorrect\n"
            + "FROM PaginatedQuestions pq\n"
            + "JOIN Answer a ON pq.QuestionID = a.QuestionID\n"
            + "ORDER BY pq.QuestionID, a.AnswerID;";
        List<Question> listQuestion = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, (page - 1) * record);
            ps.setInt(2, record);

            Question currentQuestion = null;
            int lastQuestionID = -1;

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int questionID = rs.getInt("QuestionID");

                if (questionID != lastQuestionID) {
                    currentQuestion = new Question();
                    currentQuestion.setQuestionID(questionID);
                    currentQuestion.setQuestionName(rs.getString("QuestionText"));
                    currentQuestion.setExplanation(rs.getString("Explanation"));
                    currentQuestion.setLevel(rs.getInt("Level"));
                    currentQuestion.setSubjectID(rs.getInt("SubjectID"));
                    currentQuestion.setLessonID(rs.getInt("LessonID"));
                    currentQuestion.setStatus(rs.getInt("Status"));
                    listQuestion.add(currentQuestion);
                    lastQuestionID = questionID;
                }

                if (currentQuestion != null) {
                    Answer answer = new Answer();
                    answer.setAnswerID(rs.getInt("AnswerID"));
                    answer.setQuestionID(questionID);
                    answer.setAnswerName(rs.getString("AnswerName"));
                    answer.setIsCorrect(rs.getInt("IsCorrect"));
                    currentQuestion.getAnswers().add(answer);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listQuestion;
    }
    
    public boolean  setStatus(int questionId, int status){
        String sql = "update Question\n" +
                    "set Status = ?\n" +
                    "where QuestionID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBoolean(1, status == 1); 
            ps.setInt(2, questionId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
