
package app.dal;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author hoapmhe173343
 */
public class QuestionDAO extends DBContext{
    
    public int addQuestion(String text, String explanation, int level, int subjectID, int lessonID) {
        String sql = "INSERT INTO [dbo].[Question] " +
                     "([QuestionText], [Explanation], [Level], [SubjectID], [LessonID]) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, text);
            ps.setString(2, explanation);
            ps.setInt(3, level);
            ps.setInt(4, subjectID);
            ps.setInt(5, lessonID);
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting question failed, no rows affected.");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return generated question ID
                } else {
                    throw new SQLException("Inserting question failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Return -1 if insert fails
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
    
    public static void main(String[] args) {
        QuestionDAO dao = new QuestionDAO();
        int id = dao.addQuestion("2+3= ", "", 1, 1, 1);
        System.out.println(id);
    }
}
