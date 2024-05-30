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
