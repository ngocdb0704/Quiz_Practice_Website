/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import java.util.Vector;
import app.dal.DBContext;
import app.entity.OrganizationRegistration;
import app.entity.Registration;
import app.utils.FormatData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.entity.Package;
import app.entity.Transaction;
import com.oracle.wls.shaded.java_cup.runtime.Symbol;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author admin
 */
public class DAORegistration extends DBContext {

    public Registration getSingleRegistration(int id, String email) {
        Vector<Registration> vector = new Vector<>();
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
                    where r.RegistrationId = ? and u.Email = ?""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            pre.setString(2, email);
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector.get(0);
    }

    public Vector<Integer> getStatusId() {
        Vector<Integer> vector = new Vector<>();
        String sql = "select * from RegistrationStatus";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int statusId = rs.getInt(1);
                vector.add(statusId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public Vector<Registration> multiPurposeVector(ResultSet rs) {
        Vector<Registration> vector = new Vector<>();
        try {
            //for each result in result set
            while (rs.next()) {
                Registration var = new Registration();
                var.setRegistrationId(rs.getInt(1));
                var.setSubjectName(rs.getString(2));
                var.setRegistrationTime(rs.getDate(3));
                var.setPackageName(rs.getString(4));
                var.setTotalCost(rs.getFloat(5));
                var.setStatus(rs.getString(6));
                var.setValidFrom(rs.getDate(7));
                var.setValidTo(rs.getDate(8));
                var.setSubjectImg(rs.getString(9));
                var.setSubjectId(rs.getInt(10));
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

    public String addStatusToSQL(int[] status) {
        String sql = "";
        sql += " and r.RegistrationStatusId in (";
        for (int i = 0; i < status.length; i++) {
            sql += status[i] + ",";
        }
        if (sql.endsWith(",")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        sql += ")";
        return sql;
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
            int[] parentTier1, int[] parentTier2,
            int[] parentTier3, int[] statusList,
            String inputKey) {
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
                    r.ValidFrom, r.ValidTo, s.SubjectThumbnail, s.SubjectId, sc.SubjectCategoryId as 'ParentTier3',
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
        if (statusList != null) {
            sql += addStatusToSQL(statusList);
        }
        if (inputKey != null) {
            inputKey = inputKey.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            sql += " and s.SubjectTitle like ? ESCAPE '!' ";
        }
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
        sql += " order by rs.RegistrationStatusId";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            if (inputKey != null) {
                pre.setString(2, "%" + inputKey + "%");
            }
            ResultSet rs = pre.executeQuery();
            vector = multiPurposeVector(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public float getTotalCost(String email) {
        float total = 0;
        String sql = """
                    select p.SalePrice from Registration r 
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    where  u.Email = ? 
                    and r.RegistrationStatusId = 1
                    """;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                float totalCost = rs.getFloat(1);
                total += totalCost * 1000;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public Vector<Transaction> getTransactionHistory(String email) {
        String sql = """
                    select r.TransactionCode, r.RegistrationTime,
                    r.TransactionAccount, r.TransactionContent, 
                    p.SalePrice, r.RegistrationStatusId
                    from Registration r
                    join [User] u on u.UserId = r.UserId
                    join [Package] p on p.PackageId = r.PackageId
                    where r.RegistrationStatusId in (2,3,4,5) 
                    and u.Email = ?""";
        Vector<Transaction> vector = new Vector<>();
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                String code = rs.getString(1);
                String time = rs.getString(2);
                String account = rs.getString(3);
                String content = rs.getString(4);
                float price = rs.getFloat(5);
                int status = rs.getInt(6);
                Transaction trans = new Transaction(code, time, account, content, price, status);
                vector.add(trans);
            }
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

    public Vector<OrganizationRegistration> getOrgRegistrations(String email,
            int[] parentTier1, int[] parentTier2,
            int[] parentTier3, String inputKey) {
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
                     select op.PackageName, s.SubjectId, s.SubjectTitle, s.SubjectThumbnail,
                     l.ValidFrom, l.ValidTo
                     from [OrganizationPackage] op
                     join [License] l on l.OrganizationPackageId = op.OrganizationPackageId
                     join [OrganizationMember] om on om.OrganizationId = l.OrganizationId
                     join [User] u on u.UserId = om.MemberId
                     join [OrganizationPackageSubject] ops on ops.OrganizationPackageId = l.OrganizationPackageId
                     join [Subject] s on ops.SubjectId = s.SubjectId
                     join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                     left join CategoryHierarchy ch on ch.SubjectCategoryId = sc.SubjectParentCategory
                     where l.Status = 1 and op.Status = 1 and u.Email = ?""";
        Vector<OrganizationRegistration> vector = new Vector<>();
        if (inputKey != null) {
            inputKey = inputKey.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            sql += " and s.SubjectTitle like ? ESCAPE '!' ";
        }
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
            if (inputKey != null) {
                pre.setString(2, "%" + inputKey + "%");
            }
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                OrganizationRegistration oR = new OrganizationRegistration();
                oR.setOrgPackageName(rs.getString(1));
                oR.setSubjectId(rs.getInt(2));
                oR.setSubjectTitle(rs.getString(3));
                oR.setSubjectThumbnail(rs.getString(4));
                oR.setValidFrom(rs.getDate(5));
                oR.setValidTo(rs.getDate(6));
                vector.add(oR);
            }
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

    //get duration to calculate validTo date
    public int getPackageDuration(int packageId) {
        String sql = "select PackageDuration from Package where PackageId = ?";
        int duration = 0;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, packageId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                duration = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return duration;
    }

    public int updateRegistrationPackage(int registId, int packageId) {
        int n = 0;
        String sql = """
                    UPDATE [dbo].[Registration]
                    SET [PackageId] = ?
                    WHERE [RegistrationId] = ?""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, packageId);
            pre.setInt(2, registId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    //update registration after successful transaction
    public int updateRegistrationStatus(int registId, String code, String acc) {
        int n = 0;
        int duration = getPackageDuration(registId);
        long epoch = System.currentTimeMillis() / 1000;
        String registrationTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(epoch * 1000));
        String validFrom = registrationTime;
        long epochTo = epoch + duration * 30 * 24 * 60 * 60;
        String validTo = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(epochTo * 1000));
        int status = 3;
        String[] s = code.split("\\s++");
        String codeUser = s[6];
        String content = s[0];
        String sql = """
                    UPDATE [dbo].[Registration]
                    SET [RegistrationTime] = ?,
                    [RegistrationStatusId] = ?,
                    [ValidFrom] = ?,
                    [ValidTo] = ?,
                    [TransactionContent] = ?,
                    [TransactionCode] = ?,
                    [TransactionAccount] = ?
                    WHERE [RegistrationId] = ?""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, registrationTime);
            pre.setInt(2, status);
            pre.setString(3, validFrom);
            pre.setString(4, validTo);
            pre.setString(5, content);
            pre.setString(6, codeUser);
            pre.setString(7, acc);
            pre.setInt(8, registId);
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

    public int addRegistration(int packId, int userId) {
        int n = 0;
        String sql = """
                     INSERT INTO [dbo].[Registration]
                                ([UserId]
                                ,[RegistrationTime]
                                ,[PackageId]
                                ,[RegistrationStatusId]
                                ,[ValidFrom]
                                ,[ValidTo]
                                ,[TransactionContent]
                                ,[TransactionCode]
                                ,[TransactionAccount])
                          VALUES
                                (?,null,?,1,null,null
                                ,null,null,null)""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userId);
            pre.setInt(2, packId);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public int addPaidRegistration(int packId, int userId, int duration, String account, String content) {
        int n = 0;
        DAOPackage daoPack = new DAOPackage();
        long epoch = System.currentTimeMillis() / 1000;
        String registrationTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(epoch * 1000));
        String validFrom = registrationTime;
        long epochTo = epoch + duration * 30 * 24 * 60 * 60;
        String validTo = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date(epochTo * 1000));
        int status = 3;
        String[] splitContent = content.split("\\s+");
        String sql = """
                     INSERT INTO [dbo].[Registration]
                                ([UserId]
                                ,[RegistrationTime]
                                ,[PackageId]
                                ,[RegistrationStatusId]
                                ,[ValidFrom]
                                ,[ValidTo]
                                ,[TransactionContent]
                                ,[TransactionCode]
                                ,[TransactionAccount])
                          VALUES
                                (?,?,?,?,?,?
                                ,?,?,?) """;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userId);
            pre.setString(2, registrationTime);
            pre.setInt(3, packId);
            pre.setInt(4, status);
            pre.setString(5, validFrom);
            pre.setString(6, validTo);
            pre.setString(7, splitContent[0]);
            pre.setString(8, splitContent[6]);
            pre.setString(9, account);
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Registration getSingleRegistrationByUserPackageId(int packageId, int userId) {
        Registration r = new Registration();
        String sql = """
                    select r.RegistrationId, r.RegistrationTime, r.ValidFrom, r.ValidTo from Registration r
                    where r.UserId = ? and r.PackageId = ?""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, userId);
            pre.setInt(2, packageId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                r.setRegistrationId(rs.getInt(1));
                r.setRegistrationTime(rs.getDate(2));
                r.setValidFrom(rs.getDate(3));
                r.setValidTo(rs.getDate(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public static void main(String[] args) {
        DAORegistration dao = new DAORegistration();
        System.out.println(dao.getSingleRegistrationByUserPackageId(37, 1).getValidFrom());

    }
}
