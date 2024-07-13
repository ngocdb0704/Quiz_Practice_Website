package app.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;

public class QuizInformation {
    private int quizId;
    private int subjectId;
    private String subjectName;
    private String quizName;
    private QuizLevel level;
    private int durationInMinutes;
    private int passRate;
    private QuizType type;
    private boolean published;
    private Timestamp updatedTime;
    private int numberOfAttempts;
    private String description;
    private int totalQuestion;

    public QuizInformation() {
    }
    
    public QuizInformation(ResultSet rs) throws SQLException {
        this.quizId = rs.getInt("QuizId");
        this.subjectId = rs.getInt("SubjectId");
        this.subjectName = rs.getString("SubjectTitle");
        this.quizName = rs.getString("QuizName");
        this.level = QuizLevel.fromInt(rs.getInt("Level"));
        this.durationInMinutes = rs.getInt("DurationInMinutes");
        this.passRate = rs.getInt("PassRate");
        this.type = QuizType.fromInt(rs.getInt("QuizType"));
        this.published = rs.getBoolean("IsPublished");
        this.updatedTime = rs.getTimestamp("UpdatedTime");
        this.numberOfAttempts = this.totalQuestion > 0 ? (this.quizId + 8) % 22 : 0;
        this.description = rs.getString("Description");
        this.totalQuestion = rs.getInt("TotalQuestion");
    }

    public int getQuizId() {
        return quizId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getQuizName() {
        return quizName;
    }

    public QuizLevel getLevel() {
        return level;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getPassRate() {
        return passRate;
    }

    public QuizType getType() {
        return type;
    }

    public boolean isPublished() {
        return published;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public void setLevel(QuizLevel level) {
        this.level = level;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setPassRate(int passRate) {
        this.passRate = passRate;
    }

    public void setType(QuizType type) {
        this.type = type;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    
    @Override
    public String toString() {
        return "QuizInformation{" + "quizId=" + quizId + ", subjectId=" + subjectId + ", subjectName=" + subjectName + ", quizName=" + quizName + ", level=" + level + ", durationInMinutes=" + durationInMinutes + ", passRate=" + passRate + ", type=" + type + ", published=" + published + ", updatedTime=" + updatedTime + ", numberOfAttempts=" + numberOfAttempts + ", description=" + description + ", totalQuestion=" + totalQuestion + '}';
    }
}
