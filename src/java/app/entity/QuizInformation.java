package app.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class QuizInformation {
    public enum QuizLevel {
        EASY("Easy"),
        MEDIUM("Medium"),
        HARD("Hard");

        private String label;
        private QuizLevel(String label) {
            this.label = label;
        }

        public static QuizLevel fromInt(int value) {
            return switch (value) {
                case 0 -> EASY;
                case 1 -> MEDIUM;
                case 2 -> HARD;
                default -> null;
            };
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public enum QuizType {
        SIMULATION("Simulation"),
        LESSON_QUIZ("Lesson Quiz");

        private String label;
        private QuizType(String label) {
            this.label = label;
        }

        public static QuizType fromInt(int value) {
            return switch (value) {
                case 0 -> SIMULATION;
                case 1 -> LESSON_QUIZ;
                default -> null;
            };
        }

        @Override
        public String toString() {
            return label;
        }
    }

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

    @Override
    public String toString() {
        return "QuizInformation{" + "quizId=" + quizId + ", subjectId=" + subjectId + ", subjectName=" + subjectName + ", quizName=" + quizName + ", level=" + level + ", durationInMinutes=" + durationInMinutes + ", passRate=" + passRate + ", type=" + type + ", published=" + published + ", updatedTime=" + updatedTime + '}';
    }
}
