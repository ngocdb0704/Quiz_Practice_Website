/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

import java.sql.Statement;
import java.util.Vector;
import app.dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DAORegistration extends DBContext {
    public Vector<Registration> filterBySubjectCategory(int userid, String category){
        String sql = """
                     select s.SubjectName, r.RegistrationTime, p.PackageName, r.TotalCost, r.Status, r.ValidFrom, r.ValidTo 
                     from Registration r, [User] u, [Subject] s, [Package] p 
                     where r.UserId = ? and r.SubjectId = s.SubjectId and r.PackageId = p.PackageId and s.SubjectCategory = ?""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userid);
            pre.setString(2, category);
            ResultSet rs = pre.executeQuery();
            int i = 1;
            while (rs.next()) {
                int id = i;
                String subjectName = rs.getString(1);
                Date registrationTime = rs.getDate(2);
                String packageName = rs.getString(3);
                float totalCost = rs.getFloat(4);
                String status = rs.getString(5);
                Date validFrom = rs.getDate(6);
                Date validTo = rs.getDate(7);
                Registration var = new Registration(id,
                        subjectName, registrationTime, packageName,
                        totalCost, status, validFrom, validTo);
                vector.add(var);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public Vector<Registration> getAll(int userid) {
        String sql = "select s.SubjectName, r.RegistrationTime, p.PackageName,"
                + " r.TotalCost, r.Status, r.ValidFrom, r.ValidTo from Registration r,"
                + " [Subject] s, [Package] p where r.UserId = ? "
                + "and r.SubjectId = s.SubjectId and r.PackageId = p.PackageId";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userid);
            ResultSet rs = pre.executeQuery();
            int i = 1;
            while (rs.next()) {
                int id = i;
                String subjectName = rs.getString(1);
                Date registrationTime = rs.getDate(2);
                String packageName = rs.getString(3);
                float totalCost = rs.getFloat(4);
                String status = rs.getString(5);
                Date validFrom = rs.getDate(6);
                Date validTo = rs.getDate(7);
                Registration var = new Registration(id,
                        subjectName, registrationTime, packageName,
                        totalCost, status, validFrom, validTo);
                vector.add(var);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public static void main(String[] args) {
        DAORegistration dao = new DAORegistration();
    }
}
