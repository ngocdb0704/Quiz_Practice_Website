package app.entity;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Attempt {
    private int attemptId;
    private int userId;
    private int quizId;
    private int correctCount;
    private Timestamp dueDate;

    public Attempt(ResultSet rs) throws SQLException {
        this.attemptId = rs.getInt("AttemptId");
        this.quizId = rs.getInt("QuizId");
        this.userId = rs.getInt("UserId");
        this.correctCount = rs.getInt("CorrectCount");
        this.dueDate = rs.getTimestamp("DueDate");
    }

    public int getAttemptId() {
        return attemptId;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public long getEndEpoch() {
        return this.dueDate.getTime();
    }

    public boolean isFinished() {
        return Instant.now().isAfter(this.dueDate.toInstant());
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }


    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }
}
