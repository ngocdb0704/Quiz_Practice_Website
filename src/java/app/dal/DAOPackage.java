/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import app.entity.Package;
import app.utils.FormatData;
/**
 *
 * @author admin
 */
public class DAOPackage extends DBContext{
    public Vector<Package> getSubjectPackage(String subjectName){
        FormatData fd = new FormatData();
        subjectName = fd.stringSqlFormat(subjectName);
        Vector<Package> vector = new Vector<>();
        String sql = """
                    select p.PackageId, p.PackageName, p.SalePrice from Package p
                    join Subject s on s.SubjectId = p.SubjectId
                    where p.Status = 1 and s.SubjectTitle like ? ESCAPE '!'""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, subjectName + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                float price = rs.getFloat(3);
                Package pack = new Package(id,name, price);
                vector.add(pack);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }

    public void createPackage(int subjectId, Package pack) {
        String query = "INSERT INTO [dbo].[Package] (SubjectId, PackageName, PackageDuration, ListPrice, SalePrice, Status) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, subjectId);
            statement.setString(2, pack.getPackageName());
            statement.setInt(3, pack.getDuration());
            statement.setFloat(4, pack.getListPrice());
            statement.setFloat(5, pack.getSalePrice());
            statement.setBoolean(6, pack.isActive());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modifyPackage(Package pack) {
        String query = "UPDATE [dbo].[Package] SET PackageName = ?, PackageDuration = ?, ListPrice = ?, SalePrice = ?, Status = ? WHERE PackageId = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, pack.getPackageName());
            statement.setInt(2, pack.getDuration());
            statement.setFloat(3, pack.getListPrice());
            statement.setFloat(4, pack.getSalePrice());
            statement.setBoolean(5, pack.isActive());
            statement.setInt(6, pack.getPackageId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Package getPricePackageByPackageId(int packageId) {
        String sql = """
                    select p.PackageId, p.PackageName, p.PackageDuration, p.ListPrice, p.SalePrice, p.Status from Package p
                    where p.PackageId = ?
                    """;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, packageId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Package pack = new Package();
                pack.setPackageId(rs.getInt(1));
                pack.setPackageName(rs.getString(2));
                pack.setDuration(rs.getInt(3));
                pack.setListPrice(rs.getFloat(4));
                pack.setSalePrice(rs.getFloat(5));
                pack.setActive(rs.getBoolean(6));
                return pack;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    public Vector<Package> getPricePackagesBySubjectId(int subjectId) {
        Vector<Package> vector = new Vector<>();
        
        String sql = """
                    select p.PackageId, p.PackageName, p.PackageDuration, p.ListPrice, p.SalePrice, p.Status from Package p
                    where SubjectId = ?
                    """;
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, subjectId);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Package pack = new Package();
                pack.setPackageId(rs.getInt(1));
                pack.setPackageName(rs.getString(2));
                pack.setDuration(rs.getInt(3));
                pack.setListPrice(rs.getFloat(4));
                pack.setSalePrice(rs.getFloat(5));
                pack.setActive(rs.getBoolean(6));
                vector.add(pack);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vector;
    }
    
    public Package getLowestPackageBySubjectId(int subjectId) {
        String sql = "select top 1 PackageId, PackageName, ListPrice, SalePrice from package where SubjectId = ? and Status = 1 order by SalePrice";
        
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, subjectId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                return new Package(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getFloat(4));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
        
    public Package getByPackageNameSubjectName(String packageName, String subjectName){
        FormatData fd = new FormatData();
        packageName = fd.stringSqlFormat(packageName);
        subjectName = fd.stringSqlFormat(subjectName);
        Vector<Package> vector = new Vector<>();
        String sql = """
                    select p.PackageId, p.PackageName, p.SalePrice from Package p
                    join [Subject] s on s.SubjectId = p.SubjectId
                    where s.SubjectTitle like ? ESCAPE '!' 
                     and p.PackageName like ? ESCAPE '!'""";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, subjectName + "%");
            pre.setString(2, packageName + "%");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                float price = rs.getFloat(3);
                Package pack = new Package(id,name, price);
                vector.add(pack);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector.get(0);
    }
    public static void main(String[] args) {
        DAOPackage dao = new DAOPackage();
        Vector<Package> vec = dao.getSubjectPackage("Geometry Basics to Advanced");
        for(int i=0; i<vec.size(); i++){
            System.out.println(vec.get(i).getPackageName());
        }
    }
}
