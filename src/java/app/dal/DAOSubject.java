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
    public Vector<Subject> getVectorByPage(Vector<Subject> vec,
            int start, int end) {
        Vector<Subject> outputVec = new Vector<>();
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

    public Vector<Subject> getWithToken(int[] parentTier1, int[] parentTier2,
            int[] parentTier3, String inputKey, int order) {
        Vector<Subject> vec = new Vector<>();
        int flagAND = 0;
        String sql = """
                        with CategoryHierarchy as 
                         (select SubjectCategoryId,SubjectCategoryName,
                         SubjectParentCategory from SubjectCategory 
                         where SubjectParentCategory = 0
                         union all
                         select sc.SubjectCategoryId,
                         sc.SubjectCategoryName,
                         sc.SubjectParentCategory from SubjectCategory sc
                         inner join CategoryHierarchy ch
                         on ch.SubjectCategoryId = sc.SubjectParentCategory)
                         select tableSubject.SubjectId, tableSubject.SubjectTitle, tableSubject.SubjectTagLine,
                         tableSubject.SubjectThumbnail, tablePackage.PackageName, tablePackage.ListPrice, tableLowest.SalePrice,
                         tableSubject.SubjectCategoryId as 'ParentTier3', sc.SubjectParentCategory as 'ParentTier2',
                         ch.SubjectParentCategory as 'ParentTier1'
                         from (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail,
                         s.SubjectUpdatedDate, s.SubjectCategoryId from Subject s
                         where  s.SubjectStatus = 1) tableSubject
                         left join
                         (select s.SubjectId, MIN(p.SalePrice) as 'SalePrice' from Package p
                         join Subject s on s.SubjectId = p.SubjectId
                         GROUP BY s.SubjectId) tableLowest on tableLowest.SubjectId = tableSubject.SubjectId
                         left join
                         (select p.SubjectId, p.PackageName, p.ListPrice, p.SalePrice from Package p
                         join Subject s on s.SubjectId = p.SubjectId) tablePackage on tablePackage.SubjectId= tableSubject.SubjectId
                         join [SubjectCategory] sc on sc.SubjectCategoryId = tableSubject.SubjectCategoryId
                         left join CategoryHierarchy ch on ch.SubjectCategoryId = sc.SubjectParentCategory
                         where tablePackage.SalePrice = tableLowest.SalePrice 
                              """;
        if (inputKey != null) {
            inputKey = inputKey.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            sql += " and tableSubject.SubjectTitle like ? ESCAPE '!' ";
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
        switch (order) {
            //order by updated date from oldest to newest
            case 0:
                sql += " order by tableSubject.SubjectUpdatedDate asc";
                break;
            //order by updated date from newest to oldest
            case 1:
                sql += "order by tableSubject.SubjectUpdatedDate desc";
                break;
            default:
                break;
        }
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            if (inputKey != null) {
                pre.setString(1, "%" + inputKey + "%");
            }
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectName(rs.getString(2));
                sub.setTagLine(rs.getString(3));
                sub.setThumbnail(rs.getString(4));
                sub.setLowestPackageName(rs.getString(5));
                sub.setPackageListPrice(rs.getFloat(6));
                sub.setPackageSalePrice(rs.getFloat(7));
                vec.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Subject> getFeaturedSubject() {
        Vector<Subject> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs
                    = statement.executeQuery("""
                    select tableSubject.SubjectId, tableSubject.SubjectTitle, tableSubject.SubjectTagLine,
                    tableSubject.SubjectThumbnail, tablePackage.PackageName, tablePackage.ListPrice, tableLowest.SalePrice
                    from (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail, s.SubjectUpdatedDate from Subject s
                    where  s.SubjectStatus = 1 and s.IsFeaturedSubject = 1) tableSubject 
                    left join 
                    (select s.SubjectId, MIN(p.SalePrice) as 'SalePrice' from Package p
                    join Subject s on s.SubjectId = p.SubjectId
                    GROUP BY s.SubjectId) tableLowest on tableLowest.SubjectId = tableSubject.SubjectId
                    left join 
                    (select p.SubjectId, p.PackageName, p.ListPrice, p.SalePrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId) tablePackage on tablePackage.SubjectId= tableSubject.SubjectId
                    where tablePackage.SalePrice = tableLowest.SalePrice
                    order by tableSubject.SubjectUpdatedDate desc                     
                    """);
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectName(rs.getString(2));
                sub.setTagLine(rs.getString(3));
                sub.setThumbnail(rs.getString(4));
                sub.setLowestPackageName(rs.getString(5));
                sub.setPackageListPrice(rs.getFloat(6));
                sub.setPackageSalePrice(rs.getFloat(7));
                vec.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Subject> getBigSaleSubject() {
        Vector<Subject> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs
                    = statement.executeQuery("""
                    select tableSubject.SubjectId, tableSubject.SubjectTitle, tableSubject.SubjectTagLine,
                    tableSubject.SubjectThumbnail, tablePackage.PackageName, tablePackage.ListPrice, tableLowest.SalePrice
                    from (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail, s.SubjectUpdatedDate from Subject s
                    where  s.SubjectStatus = 1) tableSubject 
                    left join 
                    (select s.SubjectId, p.SalePrice, p.ListPrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId
                    where p.SalePrice <= p.ListPrice/10*5) tableLowest on tableLowest.SubjectId = tableSubject.SubjectId
                    left join 
                    (select p.SubjectId, p.PackageName, p.ListPrice, p.SalePrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId) tablePackage on tablePackage.SubjectId= tableSubject.SubjectId
                    where tablePackage.SalePrice = tableLowest.SalePrice
                    order by tableSubject.SubjectUpdatedDate desc                        
                    """);
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectName(rs.getString(2));
                sub.setTagLine(rs.getString(3));
                sub.setThumbnail(rs.getString(4));
                sub.setLowestPackageName(rs.getString(5));
                sub.setPackageListPrice(rs.getFloat(6));
                sub.setPackageSalePrice(rs.getFloat(7));
                vec.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Subject> getNewSubject() {
        Vector<Subject> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change query due to database script's change
            ResultSet rs
                    = statement.executeQuery("""
                    select tableSubject.SubjectId, tableSubject.SubjectTitle, tableSubject.SubjectTagLine,
                    tableSubject.SubjectThumbnail, tablePackage.PackageName, tablePackage.ListPrice, tableLowest.SalePrice
                    from (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail, s.SubjectUpdatedDate from Subject s
                    where MONTH(s.SubjectUpdatedDate) >= (SELECT MONTH(CONVERT(DATE, SYSDATETIME()))-1)
                    and YEAR(s.SubjectUpdatedDate) = (SELECT YEAR(CONVERT(DATE, SYSDATETIME())))
                    and s.SubjectStatus = 1) tableSubject 
                    left join 
                    (select s.SubjectId, MIN(p.SalePrice) as 'SalePrice' from Package p
                    join Subject s on s.SubjectId = p.SubjectId
                    GROUP BY s.SubjectId) tableLowest on tableLowest.SubjectId = tableSubject.SubjectId
                    left join 
                    (select p.SubjectId, p.PackageName, p.ListPrice, p.SalePrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId) tablePackage on tablePackage.SubjectId= tableSubject.SubjectId
                    where tablePackage.SalePrice = tableLowest.SalePrice
                    order by tableSubject.SubjectUpdatedDate desc""");
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectName(rs.getString(2));
                sub.setTagLine(rs.getString(3));
                sub.setThumbnail(rs.getString(4));
                sub.setLowestPackageName(rs.getString(5));
                sub.setPackageListPrice(rs.getFloat(6));
                sub.setPackageSalePrice(rs.getFloat(7));
                vec.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

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

    //Hard-coded ids
    static int[] ListOfFeaturedSubjectId = {1, 2, 3, 4, 5};

    public List<Subject> getEnoughToDisplay(int ammoutOfSubjects) {
        List<Subject> Out = new ArrayList<>();
        String sql = "SELECT TOP (?) s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail FROM Subject s WHERE s.SubjectId in (";
        for (int i : ListOfFeaturedSubjectId) {
            sql += i + ", ";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += ")";

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
        System.out.println(test.countQuestion());
    }
    
    /**
     *
     * @author Hoapmhe173343
     */
    public List<String> getAllSubjectTitle(){
        List<String> listSubjectTitle = new ArrayList();
        String sql = "SELECT [SubjectTitle]\n" +
                    "  FROM [dbo].[Subject]";
        
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                listSubjectTitle.add(rs.getString("SubjectTitle"));
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return listSubjectTitle;
    }
    
    public int countQuestion() {
        String sql = "SELECT COUNT(*) FROM Question";
        int totalItem = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql); 
                ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalItem = rs.getInt(1);
            }
        } catch (SQLException e) {
        }

        return totalItem;
    }
}
