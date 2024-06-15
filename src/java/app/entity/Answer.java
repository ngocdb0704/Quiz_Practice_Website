
package app.entity;

/**
 *
 * @author hoapmhe173343
 */
public class Answer {
    private int answerID, questionID;
    private String answerName;
    private int isCorrect;

    public Answer() {
    }

    public Answer(int answerID, int questionID, String answerName, int isCorrect) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.answerName = answerName;
        this.isCorrect = isCorrect;
    }

    public int getAnswerID() {
        return answerID;
    }

    public void setAnswerID(int answerID) {
        this.answerID = answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "Answer{" + "answerID=" + answerID + ", questionID=" + questionID + ", answerName=" + answerName + ", isCorrect=" + isCorrect + '}';
    }
    
    
}