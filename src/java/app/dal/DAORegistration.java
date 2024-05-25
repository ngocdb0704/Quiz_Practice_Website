/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import java.util.Vector;
import app.dal.DBContext;
import app.entity.Registration;
import app.utils.FormatData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.entity.Package;

/**
 *
 * @author admin
 */
<<<<<<< HEAD
public class DAORegistration extends __local__DBContext {
    public Vector<Registration> multiPurposeVector(ResultSet rs){
=======
public class DAORegistration extends DBContext {

    public Vector<String> statusFilter(){
        Vector<String> vector = new Vector<>();
        String sql = "select DISTINCT rs.RegistrationStatusName from RegistrationStatus rs";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String status = rs.getString(1);
                vector.add(status);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public Vector<Registration> multiPurposeVector(ResultSet rs) {
>>>>>>> origin/ngocBranch
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
<<<<<<< HEAD
    public Vector<Registration> searchNameFilter(int userid, String inputSearch, String category){
=======

    public Registration getByRegistId(String email, int id) {
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    where  u.Email = ? and r.RegistrationId = ?""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setInt(2, id);
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector.get(0);
    }

    public Vector<Registration> searchNameFilter(String email, String inputSearch, String status) {
>>>>>>> origin/ngocBranch
        inputSearch = inputSearch
                .replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        Vector<Registration> vector = new Vector<>();
        String sql = """
<<<<<<< HEAD
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
=======
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    where  u.Email = ? and rs.RegistrationStatusName =  ?
                    and s.SubjectTitle like ? ESCAPE '!'""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, status);
>>>>>>> origin/ngocBranch
            pre.setString(3, inputSearch + "%");
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
<<<<<<< HEAD
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
=======

    public Vector<Registration> searchBySubjectName(String email, String inputSearch) {
        FormatData fd = new FormatData();
        inputSearch = fd.stringSqlFormat(inputSearch);
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    where  u.Email = ?
                    and s.SubjectTitle like ? ESCAPE '!'
                    order by rs.RegistrationStatusId""";
>>>>>>> origin/ngocBranch
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
<<<<<<< HEAD
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
=======

    public Vector<Registration> filterBySubjectStatus(String email, String status) {
        FormatData fd = new FormatData();
        status = fd.stringSqlFormat(status);
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    where  u.Email = ? and rs.RegistrationStatusName =  ?""";
        Vector<Registration> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            pre.setString(2, status);
>>>>>>> origin/ngocBranch
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
<<<<<<< HEAD
    public Vector<Registration> getAll(int userid) {
        String sql = """
                    select r.RegistrationId, s.SubjectName,
                    r.RegistrationTime, p.PackageName, r.TotalCost, r.Status,
                    r.ValidFrom, r.ValidTo, s.SubjectImage from Registration r,
                    [Subject] s, [Package] p where r.UserId = ? 
                     and r.SubjectId = s.SubjectId and r.PackageId = p.PackageId""";
=======

    public Vector<Registration> getAll(String email) {
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    where  u.Email = ?
                    order by rs.RegistrationStatusId""";
>>>>>>> origin/ngocBranch
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
<<<<<<< HEAD
    public int removeRegistration(int registrationId){
        int n=0;
        String sqlRemove = "DELETE FROM [dbo].[Registration]\n" +
"      WHERE RegistrationId =?";
=======

    public int removeRegistration(int registrationId) {
        int n = 0;
        String sqlRemove = "DELETE FROM [dbo].[Registration] WHERE RegistrationId =?";
>>>>>>> origin/ngocBranch
        try {
            PreparedStatement pre = connection.prepareStatement(sqlRemove);
            pre.setInt(1, registrationId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
<<<<<<< HEAD
    public static void main(String[] args) {
        DAORegistration dao = new DAORegistration();
        int n = dao.removeRegistration(1);
        System.out.println(n);
=======
    public int updateRegistration (Package pack, int registId){
        int n=0;
        String sql = """
                     UPDATE [dbo].[Registration]
                        SET [PackageId] = ?
                           ,[TotalCost] = ?
                      WHERE [RegistrationId] = ?""";
        try{
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, pack.getPackageId());
            pre.setFloat(2, pack.getSalePrice());
            pre.setInt(3, registId);
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
>>>>>>> origin/ngocBranch
    }
}
