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
public class DAORegistration extends DBContext {

    public Vector<Registration> multiPurposeVector(ResultSet rs) {
        Vector<Registration> vector = new Vector<>();
        try {
            //for each result in result set
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

    public Vector<Registration> searchNameFilter(String email, String inputSearch, String category) {
        inputSearch = inputSearch
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        Vector<Registration> vector = new Vector<>();
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r, [User] u, [Subject] s, [Package] p , 
                    [RegistrationStatus] rs, SubjectCategory sc where p.SubjectId = s.SubjectId
                    and r.PackageId = p.PackageId and sc.SubjectCategoryId = s.SubjectCategoryId
                    and r.RegistrationStatusId = rs.RegistrationStatusId 
                    and  u.Email = ? and sc.SubjectCategoryName =  ?
                    and s.SubjectTitle like ? ESCAPE '!'""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, category);
            pre.setString(3, inputSearch + "%");
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Registration> searchBySubjectName(String email, String inputSearch) {
        inputSearch = inputSearch
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r, [User] u, [Subject] s, [Package] p , 
                    [RegistrationStatus] rs, SubjectCategory sc where p.SubjectId = s.SubjectId
                    and r.PackageId = p.PackageId and sc.SubjectCategoryId = s.SubjectCategoryId
                    and r.RegistrationStatusId = rs.RegistrationStatusId 
                    and  u.Email = ?
                    and s.SubjectTitle like ? ESCAPE '!'""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, inputSearch + "%");
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Registration> filterBySubjectCategory(String email, String category) {
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r, [User] u, [Subject] s, [Package] p , 
                    [RegistrationStatus] rs, SubjectCategory sc where p.SubjectId = s.SubjectId
                    and r.PackageId = p.PackageId and sc.SubjectCategoryId = s.SubjectCategoryId
                    and r.RegistrationStatusId = rs.RegistrationStatusId 
                    and  u.Email = ? and sc.SubjectCategoryName =  ?""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, category);
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Registration> getAll(String email) {
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r, [User] u, [Subject] s, [Package] p , 
                    [RegistrationStatus] rs, SubjectCategory sc where p.SubjectId = s.SubjectId
                    and r.PackageId = p.PackageId and sc.SubjectCategoryId = s.SubjectCategoryId
                    and r.RegistrationStatusId = rs.RegistrationStatusId 
                    and  u.Email = ?""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public int removeRegistration(int registrationId) {
        int n = 0;
        String sqlRemove = "DELETE FROM [dbo].[Registration] WHERE RegistrationId =?";
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
        Vector<Registration> vec = dao.getAll("ngocdbhe182383@fpt.edu.vn");
        System.out.println(vec.get(0).getSubjectName());
    }
}
