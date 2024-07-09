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

            return getAttemptById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
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

    public static void main(String[] args) {
        DAOAttempt da = new DAOAttempt();
        Attempt before = da.createAttempt(1, 1);
        System.out.println(before.getDueDate());
        System.out.println(before.isFinished());
        da.finishAttempt(before.getAttemptId());
        before = da.getAttemptById(before.getAttemptId());
        System.out.println(before.getDueDate());
        System.out.println(before.isFinished());
    }
}
