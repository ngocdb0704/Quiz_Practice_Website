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
import app.entity.Users;

import app.dal.__local__DBContext;

/**
 *
 * @author quatn
 */
public class DAOUsers extends __local__DBContext{
    public Vector<Users> getFull(String sql) {
        Vector<Users> Out = new Vector<Users>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()) {
                Out.add(new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }
    
    public Vector<Users> getAll() {
        return getFull("select * from Users");
    }
    
    public Users getUserById(int id) {
        String sql = "SELECT * FROM Users where Id = '" + id + "';";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            if (rs.next()) {
                return new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public int updateUser(int id, String fullName, String gender, String mobile) {
        String sql = "UPDATE SWP.Users\n" +
"   SET FullName = ?\n" +
"      ,Gender = ?\n" +
"      ,Mobile = ?\n" +
" WHERE Id = ?";
                
        try {
            PreparedStatement preStat = connection.prepareStatement(sql);
             preStat.setInt(4, id);
             preStat.setString(1, fullName);
             preStat.setString(2, gender);
             preStat.setString(3, mobile);
            //preStat.executeUpdate();
            return preStat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static void main(String[] args) {
        DAOUsers test = new DAOUsers();
        System.out.println(test.getAll());
    }
}
