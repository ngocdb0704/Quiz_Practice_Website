package app.dal;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import app.entity.Question;
import app.entity.Answer;
import static app.dal.QueryBuilder.Operator;
import static app.dal.QueryBuilder.OrderDirection;

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

    public boolean setStatus(int questionId, int status) {
        String sql = "update Question\n"
                + "set Status = ?\n"
                + "where QuestionID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, status);
            ps.setInt(2, questionId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public QueryResult filters(int subjectId, int lesson, int level, int status,
            String searchContent, int page, int pageSize) {
        List<Question> ret = new ArrayList<>();
        int count = 0;
        String sql = "select * from Question";

        String sql1 = "select count(*) from Question";

        try {
            QueryBuilder query = new QueryBuilder(sql)
                    .orderBy("QuestionID", OrderDirection.ASC);
            //subject id
            if (subjectId != 0) {
                query.whereAnd("SubjectID", Operator.EQUALS, subjectId);
            }

            if (lesson != 0) {
                query.whereAnd("LessonID", Operator.EQUALS, lesson);
            }
            //search content
            if (searchContent != null && !searchContent.isBlank()) {
                query.whereAnd("QuestionText", Operator.LIKE, "%" + searchContent.trim() + "%");
            }

            //level question
            if (level != 0) {
                query.whereAnd("Level", Operator.EQUALS, level);
            }

            //status
            if (status != 0) {
                query.whereAnd("Status", Operator.EQUALS, status);
            }

            ResultSet rs = new QueryBuilder(sql1, query)
                    .toPreparedStatement(connection)
                    .executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs = query
                    .setLoggingEnabled(true)
                    .page(page, pageSize)
                    .toPreparedStatement(connection)
                    .executeQuery();

            while (rs.next()) {
                Question currentQuestion = new Question();
                currentQuestion.setQuestionID(rs.getInt("QuestionID"));
                currentQuestion.setQuestionName(rs.getString("QuestionText"));
                currentQuestion.setExplanation(rs.getString("Explanation"));
                currentQuestion.setLevel(rs.getInt("Level"));
                currentQuestion.setSubjectID(rs.getInt("SubjectID"));
                currentQuestion.setLessonID(rs.getInt("LessonID"));
                currentQuestion.setStatus(rs.getInt("Status"));
                ret.add(currentQuestion);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new QueryResult(count, pageSize, ret);
    }

    public Question getQuestion(int qid) {
        Question question = null;
        List<Answer> listAns = new ArrayList<>();
        String questionQuery = "SELECT * FROM Question WHERE questionID = ?";
        String answerQuery = "SELECT * FROM Answer WHERE questionID = ?";

        try (PreparedStatement psQuestion = connection.prepareStatement(questionQuery)) {
            psQuestion.setInt(1, qid);
            try (ResultSet rsQuestion = psQuestion.executeQuery()) {
                if (rsQuestion.next()) {
                    question = new Question();
                    question.setQuestionID(qid);
                    question.setQuestionName(rsQuestion.getString("QuestionText"));
                    question.setExplanation(rsQuestion.getString("Explanation"));
                    question.setLevel(rsQuestion.getInt("Level"));
                    question.setSubjectID(rsQuestion.getInt("SubjectID"));
                    question.setLessonID(rsQuestion.getInt("LessonID"));
                    question.setStatus(rsQuestion.getInt("status"));
                }
            }

            if (question != null) {
                try (PreparedStatement psAnswer = connection.prepareStatement(answerQuery)) {
                    psAnswer.setInt(1, qid);
                    try (ResultSet rsAnswer = psAnswer.executeQuery()) {
                        while (rsAnswer.next()) {
                            Answer answer = new Answer();
                            answer.setAnswerID(rsAnswer.getInt("AnswerID"));
                            answer.setQuestionID(qid);
                            answer.setAnswerName(rsAnswer.getString("AnswerName"));
                            answer.setIsCorrect(rsAnswer.getInt("IsCorrect"));

                            listAns.add(answer);
                        }
                    }
                }
                question.setAnswers(listAns);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return question;
    }

    public static void main(String[] args) {
        QuestionDAO q = new QuestionDAO();
        boolean rs = q.deleteAnswer(5);
        System.out.println(rs);
    }

    public boolean isExistOptionAns(int questionId, String content) {
        String sql = "SELECT * \n"
                + "FROM Answer\n"
                + "WHERE questionID = ? \n"
                + "AND CAST(AnswerName AS VARCHAR(MAX)) = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionId);
            ps.setString(2, content);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true; //exited
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean deleteAnswer(int answerID) {
        String sql = "DELETE FROM Answer WHERE AnswerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, answerID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateQuestion(Question question) {
        String sql = "UPDATE Question SET QuestionText = ?, SubjectID = ?, LessonID = ?, Level = ?, Explanation = ?, Status = ? WHERE QuestionID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, question.getQuestionName());
            ps.setInt(2, question.getSubjectID());
            ps.setInt(3, question.getLessonID());
            ps.setInt(4, question.getLevel());
            ps.setString(5, question.getExplanation());
            ps.setInt(6, question.getStatus());
            ps.setInt(7, question.getQuestionID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateAnswer(Answer answer) {
        String sql = "UPDATE Answer SET AnswerName = ?, IsCorrect = ? WHERE AnswerID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, answer.getAnswerName());
            ps.setInt(2, answer.getIsCorrect());
            ps.setInt(3, answer.getAnswerID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNumberOfOptions(int questionID) {
        int count = 0;
        String query = "SELECT COUNT(*) FROM Answer WHERE questionID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, questionID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
}
