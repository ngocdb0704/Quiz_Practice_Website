/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import java.util.Vector;
import app.dal.DBContext;
import app.entity.Registration;
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
public class DAORegistration extends __local__DBContext {
    public Vector<Registration> multiPurposeVector(ResultSet rs){
        Vector<Registration> vector = new Vector<>();
        try {
            while (rs.next()) {
                int id = rs.getInt(1);
                String subjectName = rs.getString(2);
                Date registrationTime = rs.getDate(3);
                String packageName = rs.getString(4);
                float totalCost = rs.getFloat(5);
                String status = rs.getString(6);
                Date validFrom = rs.getDate(7);
                Date validTo = rs.getDate(8);
                String img = rs.getString(9);
                Registration var = new Registration(id,
                        subjectName, registrationTime, packageName,
                        totalCost, status, validFrom, validTo, img);
                vector.add(var);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public Vector<Registration> searchNameFilter(int userid, String inputSearch, String category){
        inputSearch = inputSearch
    .replace("!", "!!")
    .replace("%", "!%")
    .replace("_", "!_")
    .replace("[", "![");
        Vector<Registration> vector = new Vector<>();
        String sql = """
                     select r.RegistrationId, s.SubjectName,
                     r.RegistrationTime, p.PackageName, r.TotalCost, r.Status,
                     r.ValidFrom, r.ValidTo, s.SubjectImage
                     from Registration r, [User] u, [Subject] s, [Package] p
                     where r.UserId = ? and r.SubjectId = s.SubjectId
                     and r.PackageId = p.PackageId and s.SubjectCategory = ? 
                     and s.SubjectName like ? ESCAPE '!'""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userid);
            pre.setString(2, category);
            pre.setString(3, inputSearch + "%");
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public Vector<Registration> searchBySubjectName(int userid, String inputSearch){
        inputSearch = inputSearch
    .replace("!", "!!")
    .replace("%", "!%")
    .replace("_", "!_")
    .replace("[", "![");
        String sql = """
                     select r.RegistrationId, s.SubjectName,
                     r.RegistrationTime, p.PackageName, r.TotalCost, r.Status,
                     r.ValidFrom, r.ValidTo, s.SubjectImage 
                     from Registration r, [User] u, [Subject] s, [Package] p
                     where r.UserId = ? and r.SubjectId = s.SubjectId
                     and r.PackageId = p.PackageId and s.SubjectName 
                     like ? ESCAPE '!'""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userid);
            pre.setString(2, inputSearch + "%");
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public Vector<Registration> filterBySubjectCategory(int userid, String category){
        String sql = """
                     select r.RegistrationId, s.SubjectName,
                     r.RegistrationTime, p.PackageName, r.TotalCost, r.Status,
                     r.ValidFrom, r.ValidTo, s.SubjectImage
                     from Registration r, [User] u, [Subject] s, [Package] p 
                     where r.UserId = ? and r.SubjectId = s.SubjectId 
                     and r.PackageId = p.PackageId and s.SubjectCategory = ?""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userid);
            pre.setString(2, category);
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public Vector<Registration> getAll(int userid) {
        String sql = """
                    select r.RegistrationId, s.SubjectName,
                    r.RegistrationTime, p.PackageName, r.TotalCost, r.Status,
                    r.ValidFrom, r.ValidTo, s.SubjectImage from Registration r,
                    [Subject] s, [Package] p where r.UserId = ? 
                     and r.SubjectId = s.SubjectId and r.PackageId = p.PackageId""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userid);
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public int removeRegistration(int registrationId){
        int n=0;
        String sqlRemove = "DELETE FROM [dbo].[Registration]\n" +
"      WHERE RegistrationId =?";
        try {
            PreparedStatement pre = connection.prepareStatement(sqlRemove);
            pre.setInt(1, registrationId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public static void main(String[] args) {
        DAORegistration dao = new DAORegistration();
        int n = dao.removeRegistration(1);
        System.out.println(n);
    }
}
