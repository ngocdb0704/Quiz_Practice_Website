
package app.entity;

/**
 *
 * @author Hoapmhe173343
 */
public class Question {
    private int questionID;
    private String questionName;
    private int level, subjectID, lessonID;

    public Question() {
    }

    public Question(int questionID, String questionName, int level, int subjectID, int lessonID) {
        this.questionID = questionID;
        this.questionName = questionName;
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

    @Override
    public String toString() {
        return "Question{" + "questionID=" + questionID + ", questionName=" + questionName + ", level=" + level + ", subjectID=" + subjectID + ", lessonID=" + lessonID + '}';
    }
    
    
}
