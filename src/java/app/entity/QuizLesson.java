package app.entity;

/**
 *
 * @author Hoapmhe173343
 */

public class QuizLesson {
    private int quizId;
    private int lessonId;
    private int questionCount;

    public QuizLesson() {
    }

    public QuizLesson(int quizId, int lessonId, int questionCount) {
        this.quizId = quizId;
        this.lessonId = lessonId;
        this.questionCount = questionCount;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    @Override
    public String toString() {
        return "QuizLesson{" + "quizId=" + quizId + ", lessonId=" + lessonId + ", questionCount=" + questionCount + '}';
    }
    
    
}
