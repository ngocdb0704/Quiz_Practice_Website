/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import app.dal.DBContext;
import app.entity.User;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOUser extends DBContext {

    public int addCustomer(User user) {
        int n = 0;
        String sql = "INSERT INTO [User]\n"
                + "           ([Id]\n"
                + "           ,[Email]\n"
                + "           ,[Password]\n"
                + "           ,[Role]\n"
                + "           ,[FullName]\n"
                + "           ,[Gender]\n"
                + "           ,[Mobile]\n"
                + "           ,[isActive]\n"
                + "     VALUES (?,?,?,?,?,?,?.?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, user.getUserId());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPassword());
            pre.setString(4, user.getRole());
            pre.setString(5, user.getFullName());
            pre.setString(6, user.getGender());
            pre.setString(7, user.getMobile());
            pre.setBoolean(8, user.isIsActive());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }

    public Vector<User> getAll(String sql) {
        Vector<User> vector = new Vector<User>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int Id = rs.getInt(1);
                String Email = rs.getString(2);
                String Password = rs.getString(3);
                String Role = rs.getString(4);
                String FullName = rs.getString(5);
                String Gender = rs.getString(6);
                String Mobile = rs.getString(7);
                Boolean isActive = rs.getBoolean(8);
                User cus = new User(Id, Email, Password, Role, FullName, Gender, Mobile, isActive);
                vector.add(cus);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return vector;
    }

    public Vector<User> searchEmail(String search) throws SQLException {
        String sql = "select * from [User] where [Email] like ?";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setString(1, search);
        return getAll(sql);
    }

    public Vector<User> searchPassword(String search) throws SQLException {
        String sql = "select * from [User] where [Password] like ?";
        PreparedStatement pre = connection.prepareStatement(sql);
        pre.setString(1, search);
        return getAll(sql);
    }

    public void updatePassByUser(String user, String pass) {
        String sql = "UPDATE [User]\n"
                + "   SET [Password] = ?\n"
                + " WHERE [Email] = ?";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setString(1, pass);
            pre.setString(2, user);
            pre.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DAOUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
