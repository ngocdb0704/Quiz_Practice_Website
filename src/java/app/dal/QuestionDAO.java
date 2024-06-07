
package app.dal;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author hoapmhe173343
 */
public class QuestionDAO extends DBContext{
    
    public int addQuestion(String text, String explanation, int level, int subjectID, int lessonID){
        String sql = "INSERT INTO [dbo].[Question]\n" +
        "           ([QuestionText]\n" +
        "           ,[Explanation]\n" +
        "           ,[Level]\n" +
        "           ,[SubjectID]\n" +
        "           ,[LessonID])\n" +
        "     VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, text);
            ps.setString(2, explanation);
            ps.setInt(3, level);
            ps.setInt(4, subjectID);
            ps.setInt(5, lessonID);
            ps.executeQuery();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Return generated question ID
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }
    
    public void addAnswer(int questionID, String answerName, int isCorrect){
        String sql = "INSERT INTO [dbo].[Answer]\n" +
            "           ([QuestionID]\n" +
            "           ,[AnswerName]\n" +
            "           ,[IsCorrect])\n" +
            "     VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, questionID);
            ps.setString(2, answerName);
            ps.setInt(3, isCorrect);
            ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
}
