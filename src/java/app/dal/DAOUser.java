/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import app.dal.DBContext;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Vector;
import java.sql.ResultSet;
import app.entity.User;

import app.dal.DBContext;

/**
 *
 * @author quatn
 */
public class DAOUser extends __local__DBContext{
    private Vector<User> getFull(String sql) {
        Vector<User> Out = new Vector<User>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()) {
                Out.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getBoolean(8)));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }
    
    public Vector<User> getAll() {
        return getFull("select * from User");
    }
    
    public User getUserById(int id) {
        String sql = "SELECT * FROM User where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getBoolean(8));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public int updateUser(int id, String fullName, String gender, String mobile) {
        String sql = "UPDATE User\n" +
"   SET FullName = ?\n" +
"      ,Gender = ?\n" +
"      ,Mobile = ?\n" +
" WHERE UserId = ?";
                
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
             preStat.setInt(4, id);
             preStat.setString(1, fullName);
             preStat.setString(2, gender);
             preStat.setString(3, mobile);
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public byte[] profileImage(int id) {
        String sql = "SELECT * FROM ProfilePicture where UserId = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return rs.getBytes(2);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public int insertImage(int id, byte[] image) {
        String sql = "INSERT INTO ProfilePicture (UserId, Image)\n"
                + "     VALUES (?,?)";
        
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
            preStat.setInt(1, id);
            preStat.setBytes(2, image);
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int updateImage(int id, byte[] image) {
        if (profileImage(id) == null) return insertImage(id, image);
        else {
            String sql = "UPDATE ProfilePicture\n"
                    + " SET Image = ?"
                    + " WHERE UserId = ?;";

            try {
                PreparedStatement preStat = connection.prepareStatement(sql);
                preStat.setInt(2, id);
                preStat.setBytes(1, image);
                return preStat.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }
    
    
    public static void main(String[] args) {
        DAOUser test = new DAOUser();
        System.out.println(test.getAll());
    }
}
