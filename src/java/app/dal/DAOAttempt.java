package app.dal;

import app.entity.Attempt;
import app.entity.QuizInformation;
import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class DAOAttempt extends DBContext {
    public Attempt createAttempt(int quizId, int userId) {
        DAOQuiz quiz = new DAOQuiz();

        try {
            QuizInformation quizInfo = quiz.getQuizById(quizId);

            if (quizInfo == null) {
                throw new Exception("Could not create attempt for an unknown quiz");
            }

            quiz.close();

            QueryBuilder builder = new QueryBuilder();
            builder.setReturnKeys(true);
            builder.insertInto("[Attempt]", "QuizId", "UserId", "DueDate");

            int duration = quizInfo.getDurationInMinutes();
            Instant ins = Instant.now().plus(duration,  ChronoUnit.MINUTES);
            
            builder.values(quizId, userId, Timestamp.from(ins));

            PreparedStatement stmt = builder.toPreparedStatement(connection);
            
            int n = stmt.executeUpdate();

            if (n != 1) {
                throw new Exception("Could not create attempt for quiz " + quizId + ", no rows were affected");
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new Exception("Could not get generated id for the created quiz");
            }

            int id = rs.getInt(1);

            Attempt attempt = getAttemptById(id);

            if (attempt == null) return null;

            prepareAnswerSheet(attempt);

            return attempt;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void prepareAnswerSheet(Attempt attempt) throws Exception {
        ResultSet rs = new QueryBuilder("select QuestionId from [QuizQuestion]")
                            .whereAnd("QuizId", QueryBuilder.Operator.EQUALS, attempt.getQuizId())
                            .toPreparedStatement(connection)
                            .executeQuery();

        QueryBuilder insert = new QueryBuilder();
        insert.insertInto("[AttemptQuestionAnswer]", "AttemptId", "QuestionId");

        while (rs.next()) {
            insert.values(attempt.getAttemptId(), rs.getInt(1));
        }

        insert.toPreparedStatement(connection).executeUpdate();
    }

    private boolean saveAttempt(Attempt attempt) {
        try {
            PreparedStatement stmt = connection.prepareStatement("update [Attempt] set [CorrectCount] = ?, [DueDate] = ? where [AttemptId] = ?");
            stmt.setInt(1, attempt.getCorrectCount());
            stmt.setTimestamp(2, attempt.getDueDate());
            stmt.setInt(3, attempt.getAttemptId());

            int n = stmt.executeUpdate();

            if (n != 1) return false;

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Attempt getAttemptById(int attemptId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from [Attempt] where [AttemptId] = ?");
            stmt.setInt(1, attemptId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Attempt(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean finishAttempt(int attemptId) {
        try {
            Attempt attempt = getAttemptById(attemptId);
            if (attempt == null) {
                throw new Exception("Could not find attempt id " + attemptId);
            }

            attempt.setDueDate(Timestamp.from(Instant.now()));

            return saveAttempt(attempt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean answerQuestion(int attemptId, int questionId, int answerId) {
        try {
            Attempt attempt = getAttemptById(attemptId);

            if (attempt == null) {
                throw new Exception("Cannot answer on non-existent attempt " + attemptId);
            }

            if (attempt.isFinished()) {
                throw new Exception("Attempt is past its due-date, cannot answer this attempt id " + attemptId);
            }

            PreparedStatement stmt = connection.prepareStatement("update [AttemptQuestionAnswer] set [AnswerId] = ? where [AttemptId] = ? and [QuestionId] = ?");

            stmt.setInt(1, answerId);
            stmt.setInt(2, attemptId);
            stmt.setInt(3, questionId);

            int n = stmt.executeUpdate();

            return n == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        DAOAttempt da = new DAOAttempt();
        Attempt attempt = da.createAttempt(1, 1);
        da.answerQuestion(attempt.getAttemptId(), 1, 2);
    }
}
