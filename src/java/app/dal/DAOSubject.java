/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import app.dal.DBContext;
import app.entity.Subject;
import app.entity.SubjectCategory;
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
public class DAOSubject extends DBContext {

    public Vector<SubjectCategory> getFilterList() {
        Vector<SubjectCategory> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change query due to database script's change
            ResultSet rs
                    = statement.executeQuery("""
                    with CategoryHierarchy as (
                        select  SubjectCategoryId, SubjectCategoryName,
                                    SubjectParentCategory from SubjectCategory 
                                    where SubjectParentCategory = 0 
                        union all
                        select  sc.SubjectCategoryId,
                                        sc.SubjectCategoryName,
                                        sc.SubjectParentCategory from SubjectCategory sc
                                        inner join CategoryHierarchy ch 
                                        on ch.SubjectCategoryId = sc.SubjectParentCategory
                    )
                    select * from CategoryHierarchy""");
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int categoryParent = rs.getInt(3);
                SubjectCategory cat = new SubjectCategory(id, name, categoryParent);
                vec.add(cat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }
}
