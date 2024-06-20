/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    // horrible code due to time constraints
    // the database used is temporary and will be changed in the next iteration
    public boolean createPackage(int subjectId, Package pack) {
        String base = "INSERT INTO [dbo].[Package] (SubjectId, PackageName, PackageDuration, ListPrice, SalePrice, Status) VALUES (?, ?, ?, ?, ?, ?)";
        
        String desc = "INSERT INTO [dbo].[PricePackageDesc] (PackageId, [Desc]) VALUES (?, ?)";

        try {
            PreparedStatement baseStmt = connection.prepareStatement(base, Statement.RETURN_GENERATED_KEYS);
            baseStmt.setInt(1, subjectId);
            baseStmt.setString(2, pack.getPackageName());
            baseStmt.setInt(3, pack.getDuration());
            baseStmt.setFloat(4, pack.getListPrice());
            baseStmt.setFloat(5, pack.getSalePrice());
            baseStmt.setBoolean(6, pack.isActive());
            int n = baseStmt.executeUpdate();
            if (n != 1) return false;
            
            ResultSet rs = baseStmt.getGeneratedKeys();
            if (!rs.next()) return false;
            
            PreparedStatement descStmt = connection.prepareStatement(desc);
            descStmt.setLong(1, rs.getLong(1));
            descStmt.setString(2, pack.getDescription());
            n = descStmt.executeUpdate();
            if (n != 1) return false;
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public boolean isDurationDuplicated(int subjectId, Package pack) {
        String query = "SELECT COUNT(*) FROM [dbo].[Package] WHERE SubjectId = ? AND PackageDuration = ? AND NOT PackageId = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);

            stmt.setInt(1, subjectId);
            stmt.setInt(2, pack.getDuration());
            stmt.setInt(3, pack.getPackageId());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public boolean modifyPackage(Package pack) {
        String base = "UPDATE [dbo].[Package] SET PackageName = ?, PackageDuration = ?, ListPrice = ?, SalePrice = ?, Status = ? WHERE PackageId = ?";
        String descUpdate = "UPDATE [dbo].[PricePackageDesc] SET [Desc] = ? WHERE PackageId = ?";
        String descInsert = "INSERT INTO [dbo].[PricePackageDesc] (PackageId, [Desc]) VALUES (?, ?)";

        try {
            connection.setAutoCommit(false);
            
            PreparedStatement baseStmt = connection.prepareStatement(base);
            baseStmt.setString(1, pack.getPackageName());
            baseStmt.setInt(2, pack.getDuration());
            baseStmt.setFloat(3, pack.getListPrice());
            baseStmt.setFloat(4, pack.getSalePrice());
            baseStmt.setBoolean(5, pack.isActive());
            baseStmt.setInt(6, pack.getPackageId());
            int n = baseStmt.executeUpdate();
            if (n != 1) {
                connection.rollback();
                return false;
            }

            PreparedStatement descStmt = connection.prepareStatement(descUpdate);
            descStmt.setString(1, pack.getDescription());
            descStmt.setInt(2, pack.getPackageId());
            n = descStmt.executeUpdate();

            if (n == 0) {
                descStmt = connection.prepareStatement(descInsert);
                descStmt.setInt(1, pack.getPackageId());
                descStmt.setString(2, pack.getDescription());
                n = descStmt.executeUpdate();
                if (n != 1) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, "Rollback failed", rollbackEx);
            }
            Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(DAOPackage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    public Package getPricePackageByPackageId(int packageId) {
        String sql = """
                    select p.PackageId, p.PackageName, p.PackageDuration, p.ListPrice, p.SalePrice, p.Status, pd.[Desc] from Package p
                    left join [PricePackageDesc] pd on p.PackageId = pd.PackageId
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
                pack.setDescription(rs.getString(7));
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
