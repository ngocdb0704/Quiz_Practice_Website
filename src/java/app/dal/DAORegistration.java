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

    public Vector<Registration> getVectorByPage(Vector<Registration> vec,
            int start, int end) {
        Vector<Registration> outputVec = new Vector<>();
        for (int i = start; i < end; i++) {
            outputVec.add(vec.get(i));
        }
        return outputVec;
    }

    public String addTierToSQL(int[] parent, int tier, int flag) {
        String sql = "";
        if (flag == 0) {
            sql += " and ";
        } else {
            sql += " or ";
        }
        switch (tier) {
            case 3: {
                sql += "sc.SubjectCategoryId in (";
                break;
            }
            case 2: {
                sql += "sc.SubjectParentCategory in (";
                break;
            }
            case 1: {
                sql += "ch.SubjectParentCategory in (";
                break;
            }
            default:
        }
        for (int i = 0; i < parent.length; i++) {
            sql += parent[i] + ",";
        }
        if (sql.endsWith(",")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        sql += ")";
        return sql;
    }

    public Vector<Registration> getById(String email,
            int[] parentTier1, int[] parentTier2, int[] parentTier3) {
        int flagAND = 0;
        String sql = """
                    with CategoryHierarchy as 
                    (select SubjectCategoryId,
                    SubjectCategoryName,
                    SubjectParentCategory from SubjectCategory 
                    where SubjectParentCategory = 0
                    union all
                    select sc.SubjectCategoryId,
                    sc.SubjectCategoryName,
                    sc.SubjectParentCategory from SubjectCategory sc
                    inner join CategoryHierarchy ch 
                    on ch.SubjectCategoryId = sc.SubjectParentCategory
                    )
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, p.SalePrice,
                    rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail, sc.SubjectCategoryId as 'ParentTier3',
                    sc.SubjectParentCategory as 'ParentTier2', ch.SubjectParentCategory as 'ParentTier1'
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    left join CategoryHierarchy ch on ch.SubjectCategoryId = sc.SubjectParentCategory
                    where  u.Email = ? """;
        Vector<Registration> vector = new Vector<>();
        if (parentTier3 != null) {
            sql += addTierToSQL(parentTier3, 3, flagAND);
            if (sql.contains("and")) {
                flagAND = 1;
            }
        }
        if (parentTier2 != null) {
            sql += addTierToSQL(parentTier2, 2, flagAND);
            if (sql.contains("and")) {
                flagAND = 1;
            }
        }
        if (parentTier1 != null) {
            sql += addTierToSQL(parentTier1, 1, flagAND);
        }
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

    public Vector<Registration> getAll(String email) {
        String sql = """
                    select r.RegistrationId, s.SubjectTitle,
                    r.RegistrationTime, p.PackageName, p.SalePrice,
                    rs.RegistrationStatusName,
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail 
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    join [Subject] s on s.SubjectId = p.SubjectId
                    join [RegistrationStatus] rs on rs.RegistrationStatusId = r.RegistrationStatusId
                    where  u.Email = ?
                    order by rs.RegistrationStatusId""";
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

    public int updateRegistration(Package pack, int registId) {
        int n = 0;
        String sql = """
                     UPDATE [dbo].[Registration]
                        SET [PackageId] = ?
                           ,[TotalCost] = ?
                      WHERE [RegistrationId] = ?""";
        try {
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
        int[] lmao = new int[1];
        lmao[0] = 2;
        Vector<Registration> vec = dao.getById("ngocdbhe182383@fpt.edu.vn", lmao, null, null);
        System.out.println(vec.get(0).getSubjectName());
    }
}
