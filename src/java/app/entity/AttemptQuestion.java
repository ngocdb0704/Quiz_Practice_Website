package app.entity;

import java.sql.*;

public class AttemptQuestion {
    private int attemptId;
    private Question question;
    private int selectedAnswer;
    private boolean marked;

    public AttemptQuestion(ResultSet rs) throws SQLException {
        this.attemptId = rs.getInt("AttemptId");
        this.question = new Question();
        this.question.setQuestionID(rs.getInt("QuestionId"));
        this.selectedAnswer = rs.getInt("AnswerId");
        this.marked = rs.getBoolean("Marked");
    }

    public int getAttemptId() {
        return attemptId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public boolean isAnswered() {
        return selectedAnswer != 0;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public boolean isMarked() {
        return marked;
    }
}
