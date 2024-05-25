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
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
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
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    where  u.Email = ? and sc.SubjectCategoryName =  ?
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
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    where  u.Email = ?
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
        FormatData fd = new FormatData();
        category = fd.stringSqlFormat(category);
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, r.TotalCost, rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    where  u.Email = ? and sc.SubjectCategoryName =  ?""";
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
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    where  u.Email = ?""";
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
    public int updateRegistration (Registration newRegist){
        int n=0;
        String sql = """
                     UPDATE [dbo].[Registration]
                        SET [UserId] = ?
                           ,[RegistrationTime] = ?
                           ,[PackageId] = ?
                           ,[TotalCost] = ?
                           ,[RegistrationStatusId] = ?
                           ,[ValidFrom] = ?
                           ,[ValidTo] = ?
                      WHERE [RegistrationId] = ?""";
        try{
            PreparedStatement pre = connection.prepareStatement(sql);
            //pre.setDataType(indexOf?, value);
//            pre.setString(1, newRegist.get);
//            pre.setInt(2,pro.getModel_year());
//            pre.setDouble(3, pro.getList_price());
//            pre.setString(4, pro.getBrand_name());
//            pre.setString(5, pro.getCategory_name());
//            pre.setInt(6, pro.getProduct_id());
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
