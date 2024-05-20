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
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DAOSubject extends __local__DBContext {

    public Vector<Subject> getFilterList() {
        Vector<Subject> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("select * from Subject");
            String alreadyAddedCategory = "";
            int i=1;
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
}
