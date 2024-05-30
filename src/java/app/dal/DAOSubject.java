/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import app.dal.DBContext;
import app.entity.Subject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DAOSubject extends DBContext {

    public Vector<Subject> getFilterList() {
        Vector<Subject> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change query due to database script's change
            ResultSet rs
                    = statement.executeQuery("""
                    select s.SubjectId, s.SubjectTitle, sc.SubjectCategoryName from Subject s 
                    join SubjectCategory sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    join Package p on p.SubjectId = s.SubjectId
                    join Registration r on r.PackageId = p.PackageId
                    join [User] u on r.UserId = u.UserId""");
            String alreadyAddedCategory = "";
            int i = 1;
            while (rs.next()) {
                int id = i;
                String name = rs.getString(2);
                String category = rs.getString(3);
                if (!alreadyAddedCategory.contains(category)) {
                    Subject sub = new Subject(id, name, category);
                    vec.add(sub);
                    alreadyAddedCategory += category + "/";
                    i++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }
    
    public List<Subject> getEnoughToDisplay(int ammoutOfSubjects) {
        List<Subject> Out = new ArrayList<>();
        String sql = "SELECT TOP (?) s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail FROM Subject s;";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, ammoutOfSubjects);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject a = new Subject();
                a.setSubjectId(rs.getInt(1));
                a.setSubjectName(rs.getString(2));
                a.setTagLine(rs.getString(3));
                a.setThumbnail(rs.getString(4));
                Out.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Out;
    }
    
    public static void main(String[] args) {
        DAOSubject test = new DAOSubject();
        System.out.println(test.getEnoughToDisplay(1).size());
    }
}
