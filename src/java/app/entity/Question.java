
package app.entity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hoapmhe173343
 */
public class Question {
    private int questionID;
    private String questionName, explanation;
    private int level, subjectID, lessonID;
    private List<Answer> answers;
    public Question() {
        this.answers = new ArrayList<>();
    }

    public Question(int questionID, String questionName, String explanation, int level, int subjectID, int lessonID) {
        this.questionID = questionID;
        this.questionName = questionName;
        this.explanation = explanation;
        this.level = level;
        this.subjectID = subjectID;
        this.lessonID = lessonID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getLessonID() {
        return lessonID;
    }

    public void setLessonID(int lessonID) {
        this.lessonID = lessonID;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" + "questionID=" + questionID + ", questionName=" + questionName + ", explanation=" + explanation + ", level=" + level + ", subjectID=" + subjectID + ", lessonID=" + lessonID + ", answers=" + answers + '}';
    }
    
    
    
    
}
