/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import app.dal.DBContext;
import app.entity.Organization;
import app.dto.SubjectDTO;
import app.entity.Subject;
import app.entity.Package;
import app.entity.SubjectCategory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author admin
 */
public class DAOSubject extends DBContext {

    public Vector<Subject> getRegisteredSubjects(String email) {
        Vector<Subject> vec = new Vector<>();
        String sql = """
                     select s.SubjectId, s.SubjectTitle from [Registration] r
                     join [User] u on r.UserId = u.UserId
                     join Package p on p.PackageId = r.PackageId
                     join [Subject] s on p.SubjectId = s.SubjectId
                     where u.Email like ? ESCAPE '!'""";
        email = email.replace("!", "!!")
                .replace("%", "!%")
                .replace("_", "!_")
                .replace("[", "![");
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            if (email != null) {
                pre.setString(1, "%" + email + "%");
            }
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Subject sub = new Subject();
                sub.setSubjectId(rs.getInt(1));
                sub.setSubjectName(rs.getString(2));
                vec.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Subject> getVectorByPage(Vector<Subject> vec,
            int start, int end) {
        Vector<Subject> outputVec = new Vector<>();
        for (int i = start; i < end; i++) {
            outputVec.add(vec.get(i));
        }
        return outputVec;
    }

    public String addTierToSQLForIndividual(int[] parent, int tier, int flag) {
        String sql = "";
        if (flag == 0) {
            sql += " and( ";
        } else {
            sql += " or ";
        }
        switch (tier) {
            case 3: {
                sql += "tableSubject.ParentTier3 in (";
                break;
            }
            case 2: {
                sql += "tableSubject.ParentTier2 in (";
                break;
            }
            case 1: {
                sql += "tableSubject.ParentTier1 in (";
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

    public String addTierToSQLForBusiness(int[] parent, int tier, int flag) {
        String sql = "";
        if (flag == 0) {
            sql += " and( ";
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

    public Vector<Subject> getForBusiness(int[] parentTier1, int[] parentTier2,
            int[] parentTier3, String inputKey, int order, int[] level, int[] org) {
        Vector<Subject> vec = new Vector<>();
        int flagAND = 0;
        String sql = """
                    with CategoryHierarchy as 
                    (select SubjectCategoryId,SubjectCategoryName,
                    SubjectParentCategory from SubjectCategory
                    where SubjectParentCategory = 0
                    union all
                    select sc.SubjectCategoryId,sc.SubjectCategoryName,sc.SubjectParentCategory 
                    from SubjectCategory sc
                    inner join CategoryHierarchy ch 
                    on ch.SubjectCategoryId = sc.SubjectParentCategory)
                    select s.SubjectId, s.SubjectTitle, s.SubjectTagLine,s.SubjectThumbnail,
                    sl.SubjectLevelName, o.OrganizationName              
                    from [Subject] s
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    join [SubjectLevel] sl on sl.SubjectLevelId = s.SubjectLevelId
                    join [Organization] o on o.OrganizationId = s.SubjectProviderId
                    left join CategoryHierarchy ch on ch.SubjectCategoryId = sc.SubjectParentCategory 
                    where s.SubjectStatus = 1 
                              """;
        if (parentTier3 != null) {
            sql += addTierToSQLForBusiness(parentTier3, 3, flagAND);
            if (sql.contains("and(")) {
                flagAND = 1;
            }
        }
        if (parentTier2 != null) {
            sql += addTierToSQLForBusiness(parentTier2, 2, flagAND);
            if (sql.contains("and(")) {
                flagAND = 1;
            }
        }
        if (parentTier1 != null) {
            sql += addTierToSQLForBusiness(parentTier1, 1, flagAND);
            if (sql.contains("and(")) {
                flagAND = 1;
            }
        }
        if (flagAND == 1) {
            sql += ")";
        }
        if (level != null) {
            sql += " and s.SubjectLevelId in(";
            for (int i = 0; i < level.length; i++) {
                sql += level[i] + ",";
            }
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.length() - 1);
            }
            sql += ")";
        }
        if (org != null) {
            sql += " and o.OrganizationId in(";
            for (int i = 0; i < org.length; i++) {
                sql += org[i] + ",";
            }
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.length() - 1);
            }
            sql += ")";
        }
        if (inputKey != null) {
            inputKey = inputKey.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            sql += " and s.SubjectTitle like ? ESCAPE '!' ";
        }
        switch (order) {
            //order by updated date from oldest to newest
            case 0:
                sql += " order by s.SubjectUpdatedDate asc";
                break;
            //order by updated date from newest to oldest
            case 1:
                sql += "order by s.SubjectUpdatedDate desc";
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
                sub.setLevel(rs.getString(5));
                sub.setProvider(rs.getString(6));
                vec.add(sub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Subject> getForIndividual(int[] parentTier1, int[] parentTier2,
            int[] parentTier3, String inputKey, int order, int[] level) {
        Vector<Subject> vec = new Vector<>();
        int flagAND = 0;
        String sql = """
                     with CategoryHierarchy as 
                     (select SubjectCategoryId,SubjectCategoryName,
                     SubjectParentCategory from SubjectCategory
                     where SubjectParentCategory = 0
                     union all
                     select sc.SubjectCategoryId,sc.SubjectCategoryName,sc.SubjectParentCategory 
                     from SubjectCategory sc
                     inner join CategoryHierarchy ch 
                     on ch.SubjectCategoryId = sc.SubjectParentCategory)					
                     select tableLowest.SubjectId, tableSubject.SubjectTitle,
                     tableSubject.SubjectTagLine, tableSubject.SubjectThumbnail,
                     tableSubject.PackageName, tableSubject.ListPrice,
                     tableSubject.SalePrice, tableSubject.ParentTier3,
                     tableSubject.ParentTier2, tableSubject.ParentTier1,
                     tableSubject.SubjectLevelName from 
                     (select s.SubjectId, MIN(p.SalePrice) as 'SalePrice' from Package p
                     join Subject s on s.SubjectId = p.SubjectId
                     GROUP BY s.SubjectId ) tableLowest
                     left join 
                     (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine,s.SubjectThumbnail,
                     p.PackageName, p.ListPrice, p.SalePrice, sc.SubjectCategoryId as 'ParentTier3',
                     sc.SubjectParentCategory as 'ParentTier2', ch.SubjectParentCategory as 'ParentTier1'
                     ,s.SubjectUpdatedDate, sl.SubjectLevelName, sl.SubjectLevelId, s.SubjectStatus                       
                     from [Subject] s
                     join [Package] p on p.SubjectId = s.SubjectId
                     join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                     join [SubjectLevel] sl on sl.SubjectLevelId = s.SubjectLevelId
                     left join CategoryHierarchy ch on ch.SubjectCategoryId = sc.SubjectParentCategory) tableSubject
                     on tableLowest.SubjectId = tableSubject.SubjectId
                     where tableLowest.SalePrice = tableSubject.SalePrice
                     and tableSubject.SubjectStatus = 1
                              """;
        if (parentTier3 != null) {
            sql += addTierToSQLForIndividual(parentTier3, 3, flagAND);
            if (sql.contains("and(")) {
                flagAND = 1;
            }
        }
        if (parentTier2 != null) {
            sql += addTierToSQLForIndividual(parentTier2, 2, flagAND);
            if (sql.contains("and(")) {
                flagAND = 1;
            }
        }
        if (parentTier1 != null) {
            sql += addTierToSQLForIndividual(parentTier1, 1, flagAND);
            if (sql.contains("and(")) {
                flagAND = 1;
            }
        }
        if (flagAND == 1) {
            sql += ")";
        }
        if (level != null) {
            sql += " and tableSubject.SubjectLevelId in(";
            for (int i = 0; i < level.length; i++) {
                sql += level[i] + ",";
            }
            if (sql.endsWith(",")) {
                sql = sql.substring(0, sql.length() - 1);
            }
            sql += ")";
        }
        if (inputKey != null) {
            inputKey = inputKey.replace("!", "!!")
                    .replace("%", "!%")
                    .replace("_", "!_")
                    .replace("[", "![");
            sql += " and tableSubject.SubjectTitle like ? ESCAPE '!' ";
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
                    with CategoryHierarchy as 
                    (select SubjectCategoryId,SubjectCategoryName,
                    SubjectParentCategory from SubjectCategory
                    where SubjectParentCategory = 0
                    union all
                    select sc.SubjectCategoryId,sc.SubjectCategoryName,sc.SubjectParentCategory 
                    from SubjectCategory sc
                    inner join CategoryHierarchy ch 
                    on ch.SubjectCategoryId = sc.SubjectParentCategory)					
                    select tableLowest.SubjectId, tableSubject.SubjectTitle,
                    tableSubject.SubjectTagLine, tableSubject.SubjectThumbnail,
                    tableSubject.PackageName, tableSubject.ListPrice,
                    tableSubject.SalePrice, tableSubject.ParentTier3,
                    tableSubject.ParentTier2, tableSubject.ParentTier1,
                    tableSubject.SubjectLevelName, tableSubject.OrganizationName from 
                    (select s.SubjectId, MIN(p.SalePrice) as 'SalePrice' from Package p
                    join Subject s on s.SubjectId = p.SubjectId
                    GROUP BY s.SubjectId ) tableLowest
                    left join 
                    (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine,s.SubjectThumbnail,
                    p.PackageName, p.ListPrice, p.SalePrice, sc.SubjectCategoryId as 'ParentTier3',
                    sc.SubjectParentCategory as 'ParentTier2', ch.SubjectParentCategory as 'ParentTier1'
                    ,s.SubjectUpdatedDate, sl.SubjectLevelName, sl.SubjectLevelId, o.OrganizationName, s.SubjectStatus                     
                    from [Subject] s
                    join [Package] p on p.SubjectId = s.SubjectId
                    join [SubjectCategory] sc on sc.SubjectCategoryId = s.SubjectCategoryId
                    join [SubjectLevel] sl on sl.SubjectLevelId = s.SubjectLevelId
                    left join CategoryHierarchy ch on ch.SubjectCategoryId = sc.SubjectParentCategory
                    left join [Organization] o on o.OrganizationId = s.SubjectProviderId
                    where s.IsFeaturedSubject = 1) tableSubject
                    on tableLowest.SubjectId = tableSubject.SubjectId
                    where tableLowest.SalePrice = tableSubject.SalePrice
                    and tableSubject.SubjectStatus = 1                         
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
                sub.setLevel(rs.getString(11));
                sub.setProvider(rs.getString(12));
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
                    from (select s.SubjectId, p.SalePrice, p.ListPrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId
                    where p.SalePrice <= p.ListPrice/10*5) tableLowest 
                    left join
                    (select s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail, s.SubjectUpdatedDate from Subject s
                    where  s.SubjectStatus = 1) tableSubject on tableLowest.SubjectId = tableSubject.SubjectId
                    left join 
                    (select p.SubjectId, p.PackageName, p.ListPrice, p.SalePrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId) tablePackage on tablePackage.SubjectId= tableLowest.SubjectId
                    where tablePackage.ListPrice = tableLowest.ListPrice
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

    public Vector<SubjectCategory> getLevelList() {
        Vector<SubjectCategory> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change query due to database script's change
            ResultSet rs
                    = statement.executeQuery("select sl.SubjectLevelId,  sl.SubjectLevelName  from [SubjectLevel] sl");
            while (rs.next()) {
                SubjectCategory cat = new SubjectCategory();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                cat.setCateId(id);
                cat.setCateName(name);
                vec.add(cat);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Organization> getOrgList() {
        Vector<Organization> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change query due to database script's change
            ResultSet rs
                    = statement.executeQuery("select o.OrganizationId, o.OrganizationName from [Organization] o");
            while (rs.next()) {
                Organization o = new Organization();
                o.setOrgId(rs.getInt(1));
                o.setOrgName(rs.getString(2));
                vec.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public Vector<Organization> getOrgListOfUser(String email) {
        Vector<Organization> vec = new Vector<>();
        String sql = """
                    select o.OrganizationId, o.OrganizationName from [Organization] o
                    join [OrganizationMember] om on om.MemberId = o.OrganizationId
                    join [User] u on u.UserId = om.MemberId
                    where u.Email = """;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            //change query due to database script's change
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Organization o = new Organization();
                o.setOrgId(rs.getInt(1));
                o.setOrgName(rs.getString(2));
                vec.add(o);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vec;
    }

    public HashMap<Integer, String> getSponsor(String email) {
        HashMap<Integer, String> map = new HashMap<>();
        String sql = """
                     select ops.SubjectId, o.OrganizationName from [Organization] o
                     join [OrganizationMember] om on om.MemberId = o.OrganizationId
                     join [User] u on u.UserId = om.MemberId
                     join [License] l on l.OrganizationId = o.OrganizationId
                     join [OrganizationPackageSubject] ops on ops.OrganizationPackageId = l.OrganizationPackageId
                     where u.Email = ?""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            //change query due to database script's change
            pre.setString(1, email);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int subjectId = rs.getInt(1);
                String org = rs.getString(2);
                map.put(subjectId, org);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public Vector<Package> getPlans() {
        Vector<Package> vec = new Vector<>();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //change query due to database script's change
            ResultSet rs
                    = statement.executeQuery("""
                                             select o.OrganizationPackageId, o.PackageName, 
                                             o.RetailPriceEach, o.NonprofitPriceEach from [OrganizationPackage] o
                                             where o.PackageDuration = 4""");
            while (rs.next()) {
                Package p = new Package();
                p.setPackageId(rs.getInt(1));
                p.setPackageName(rs.getString(2));
                p.setListPrice(rs.getFloat(3));
                p.setSalePrice(rs.getFloat(4));
                vec.add(p);
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

    public Subject getSubjectById(int id) {
        Subject Out = null;
        String sql = "SELECT TOP 1 SubjectId, SubjectTitle, SubjectTagLine, SubjectBriefInfo, SubjectDescription, SubjectThumbnail, SubjectCategoryId FROM Subject WHERE SubjectId = ?";

        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, id);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                Out = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Out;
    }

    public List<SubjectCategory> getSubjectCategoryLineById(int id) {
        List<SubjectCategory> Out = new ArrayList<>();
        String sql = "SELECT TOP 1 SubjectCategoryId, SubjectCategoryName, SubjectParentCategory from SubjectCategory where SubjectCategoryId = ?";

        PreparedStatement pre;
        try {
            int parentId = id, emergencyExit = 12; //just in case
            while (parentId > 0 && emergencyExit-- > 0) {
                pre = connection.prepareStatement(sql);
                pre.setInt(1, parentId);
                ResultSet rs = pre.executeQuery();
                if (rs.next()) {
                    parentId = rs.getInt(3);
                    Out.add(new SubjectCategory(rs.getInt(1), rs.getString(2), parentId));

                } else {
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Out;
    }

    public List<SubjectCategory> getAllSubjectCategories() {
        List<SubjectCategory> Out = new ArrayList<>();
        String sql = "SELECT SubjectCategoryId, SubjectCategoryName, SubjectParentCategory from SubjectCategory";

        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Out.add(new SubjectCategory(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Out;
    }

    public int addSubject(Subject sub, int ownerId, int published, int featured) {
        String sql = "INSERT INTO [Subject] VALUES(?, 1, ?, ?, 1, ?, CAST(GETDATE() as DATE), CAST(GETDATE() as DATE), ?, ?, ?, ?, ?);";

        PreparedStatement pre;
        try {
            System.out.println("Adding" + sub.toString() + "oId=" + ownerId + "published=" + published + "featured=" + featured);
            pre = connection.prepareStatement(sql);
            pre.setString(1, sub.getSubjectName());
            pre.setInt(2, sub.getCategoryId());
            pre.setInt(3, published);
            pre.setInt(4, featured);
            pre.setString(5, sub.getTagLine());
            pre.setString(6, sub.getBriefInfo());
            pre.setString(7, sub.getSubjectDescription());
            pre.setString(8, sub.getThumbnail());
            pre.setInt(9, ownerId);
            return pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public List<Subject> getFeaturedSubjects(int ammoutOfSubjects) {
        List<Subject> Out = new ArrayList<>();
        String sql = "SELECT TOP (?) s.SubjectId, s.SubjectTitle, s.SubjectTagLine, s.SubjectThumbnail FROM Subject s WHERE s.IsFeaturedSubject = 1";

        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, ammoutOfSubjects);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                //Couldn't overload a contructor for this specific purpose lol
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

    public boolean updateSubject(Subject subject) {
        boolean isUpdated = false;
        String sql = "UPDATE Subject SET SubjectTitle = ?, SubjectTagLine = ?, SubjectThumbnail = ? WHERE SubjectId = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, subject.getSubjectName());
            pre.setString(2, subject.getTagLine());
            pre.setString(3, subject.getThumbnail());
            pre.setInt(4, subject.getSubjectId());

            int rowsAffected = pre.executeUpdate();
            isUpdated = rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUpdated;
    }

    public SubjectDTO getSubjectByDTOId(int subjectId) {
        SubjectDTO subject = null;
        String sql = "SELECT [SubjectId], [SubjectTitle], [SubjectCategoryId], [SubjectStatus], "
                + "[SubjectLevelId], [IsFeaturedSubject], [SubjectCreatedDate], [SubjectUpdatedDate], "
                + "[SubjectTagLine], [SubjectBriefInfo], [SubjectDescription], [SubjectThumbnail] "
                + "FROM [dbo].[Subject] WHERE SubjectId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, subjectId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                subject = new SubjectDTO();
                subject.setSubjectId(rs.getInt("SubjectId"));
                subject.setSubjectTitle(rs.getString("SubjectTitle"));
                subject.setSubjectCategoryId(rs.getInt("SubjectCategoryId"));
                subject.setSubjectStatus(rs.getInt("SubjectStatus"));
                subject.setSubjectLevelId(rs.getInt("SubjectLevelId"));
                subject.setIsFeaturedSubject(rs.getBoolean("IsFeaturedSubject"));
                subject.setSubjectCreatedDate(rs.getTimestamp("SubjectCreatedDate"));
                subject.setSubjectUpdatedDate(rs.getTimestamp("SubjectUpdatedDate"));
                subject.setSubjectTagLine(rs.getString("SubjectTagLine"));
                subject.setSubjectBriefInfo(rs.getString("SubjectBriefInfo"));
                subject.setSubjectDescription(rs.getString("SubjectDescription"));
                subject.setSubjectThumbnail(rs.getString("SubjectThumbnail"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subject;
    }

    public boolean updateSubject(SubjectDTO subject) {
        String sql = "UPDATE dbo.Subject SET "
                + "SubjectTitle = ?, SubjectCategoryId = ?, SubjectStatus = ?, "
                + "SubjectLevelId = ?, IsFeaturedSubject = ?, SubjectBriefInfo = ?, "
                + "SubjectDescription = ?, SubjectThumbnail = ? "
                + "WHERE SubjectId = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, subject.getSubjectTitle());
            pstmt.setInt(2, subject.getSubjectCategoryId());
            pstmt.setInt(3, subject.getSubjectStatus());
            pstmt.setInt(4, subject.getSubjectLevelId());
            pstmt.setBoolean(5, subject.isIsFeaturedSubject());
            pstmt.setString(6, subject.getSubjectBriefInfo());
            pstmt.setString(7, subject.getSubjectDescription());
            pstmt.setString(8, subject.getSubjectThumbnail());
            pstmt.setInt(9, subject.getSubjectId());

            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Subject> getAllSubject() {
        List<Subject> listSubject = new ArrayList();
        String sql = "SELECT *\n"
                + "  FROM [dbo].[Subject]";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listSubject.add(new Subject(rs.getInt("subjectId"),
                        rs.getString("subjectTitle")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return listSubject;
    }

    public int countQuestion() {
        String sql = "SELECT COUNT(*) FROM Question";
        int totalItem = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                totalItem = rs.getInt(1);
            }
        } catch (SQLException e) {
        }

        return totalItem;
    }

    public HashMap<Integer, ArrayList<Package>> getSubjectPackagesMap() {
        HashMap<Integer, ArrayList<Package>> map = new HashMap<>();
        String sqlGetSubjects = "select s.SubjectId from [Subject] s";
        String sqlGetPackages = "select * from [Package] p where p.Status = 1 and p.SubjectId = ";
        int key;

        try {
            Statement statementSubject = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rsSubjects
                    = statementSubject.executeQuery(sqlGetSubjects);
            while (rsSubjects.next()) {
                key = rsSubjects.getInt(1);
                ArrayList<Package> value = new ArrayList<>();
                try {
                    Statement statementPackage = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rsPackages
                            = statementPackage.executeQuery(sqlGetPackages + key);
                    while (rsPackages.next()) {
                        Package p = new Package();
                        p.setPackageId(rsPackages.getInt(1));
                        p.setPackageName(rsPackages.getString(3));
                        p.setDuration(rsPackages.getInt(4));
                        p.setListPrice(rsPackages.getFloat(5));
                        p.setSalePrice(rsPackages.getFloat(6));
                        value.add(p);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
                }
                Collections.sort(value, new Comparator<Package>() {
                    @Override
                    public int compare(Package o1, Package o2) {
                        return o1.getDuration() > o2.getDuration() ? 1 : -1;
                    }
                });
                float baseWorth = value.get(0).getSalePrice() / value.get(0).getDuration();
                value.get(0).setWorth(0);
                for (int i = 1; i < value.size(); i++) {
                    float packWorth = value.get(i).getSalePrice() / value.get(i).getDuration();
                    int percentOfWorth = (int) ((1 - packWorth / baseWorth) * 100);
                    value.get(i).setWorth(percentOfWorth);
                }
                map.put(key, value);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOSubject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public static void main(String[] args) {
        DAOSubject dao = new DAOSubject();
        HashMap<Integer, ArrayList<Package>> map = dao.getSubjectPackagesMap();
        
    }
}
