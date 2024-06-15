/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dal;

import app.entity.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import app.dal.DBContext;

/**
 *
 * @author Administrator
 */
public class DAOCustomer extends DBContext {

    public Vector<Customer> getAll(String sql) {
        Vector<Customer> vector = new Vector<Customer>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int cid = rs.getInt(1);
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String role = rs.getString(4);
                String fname = rs.getString(5);
                String gender = rs.getString(6);
                String mobile = rs.getString(7);
                boolean isactive = rs.getBoolean(8);
                Customer cus = new Customer(cid, email, pass, role, fname, gender, mobile, isactive);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public Vector<Customer> sortby(String sql) {
        Vector<Customer> vector = new Vector<Customer>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int cid = rs.getInt(1);
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String role = rs.getString(4);
                String fname = rs.getString(5);
                String gender = rs.getString(6);
                String mobile = rs.getString(7);
                boolean isactive = rs.getBoolean(8);
                Customer cus = new Customer(cid, email, pass, role, fname, gender, mobile, isactive);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public Vector<Customer> searchbyName(String fullName) {
        Vector<Customer> vector = new Vector<Customer>();
        String sql = "select * from [User] where FullName like '%" + fullName + "%'";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int uid = rs.getInt(1);
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String role = rs.getString(4);
                String fname = rs.getString(5);
                String gender = rs.getString(6);
                String mobile = rs.getString(7);
                boolean isactive = rs.getBoolean(8);
                Customer cus = new Customer(uid, email, pass, role, fname, gender, mobile, isactive);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public Vector<Customer> searchbyEmail(String inputemail) {
        Vector<Customer> vector = new Vector<Customer>();
        String sql = "select * from [User] where Email like '%" + inputemail + "%'";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int uid = rs.getInt(1);
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String role = rs.getString(4);
                String fname = rs.getString(5);
                String gender = rs.getString(6);
                String mobile = rs.getString(7);
                boolean isactive = rs.getBoolean(8);
                Customer cus = new Customer(uid, email, pass, role, fname, gender, mobile, isactive);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public Vector<Customer> searchbyMobile(String mb) {
        Vector<Customer> vector = new Vector<Customer>();
        String sql = "select * from [User] where Mobile like '%" + mb + "%'";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                int uid = rs.getInt(1);
                String email = rs.getString(2);
                String pass = rs.getString(3);
                String role = rs.getString(4);
                String fname = rs.getString(5);
                String gender = rs.getString(6);
                String mobile = rs.getString(7);
                boolean isactive = rs.getBoolean(8);
                Customer cus = new Customer(uid, email, pass, role, fname, gender, mobile, isactive);
                vector.add(cus);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return vector;
    }

    public int addCustomers(Customer cus) {
        int n = 0;
        String sql = "INSERT INTO [dbo].[User]\n"
                + "           ([UserId]\n"
                + "           ,[Email]\n"
                + "           ,[Password]\n"
                + "           ,[RoleId]\n"
                + "           ,[FullName]\n"
                + "           ,[GenderId]\n"
                + "           ,[Mobile]\n"
                + "           ,[IsActive])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setInt(1, cus.getUserId());
            pre.setString(2, cus.getEmail());
            pre.setString(3, cus.getPassword());
            pre.setString(4, cus.getRoleId());
            pre.setString(5, cus.getFullName());
            pre.setString(6, cus.getGenderId());
            pre.setString(7, cus.getMobile());
            pre.setBoolean(8, cus.isIsActive());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return n;
    }

    public int updateUser(Customer cus) {
        int n = 0;
        String sql = "UPDATE [dbo].[User]\n"
                + "  SET  [Email] = ?\n"
                + "      ,[Password] = ?\n"
                + "      ,[RoleId] = ?\n"
                + "      ,[FullName] = ?\n"
                + "      ,[GenderId] = ?\n"
                + "      ,[Mobile] = ?\n"
                + "      ,[isActive]=?\n"
                + " WHERE UserId = ?";
        try {
            PreparedStatement pre = connection.prepareStatement(sql);
            pre.setString(1, cus.getEmail());
            pre.setString(2, cus.getPassword());
            pre.setString(3, cus.getRoleId());
            pre.setString(4,cus.getFullName());
            pre.setString(5, cus.getGenderId());
            pre.setString(6, cus.getMobile());
            pre.setBoolean(7, cus.isIsActive());
            pre.setInt(8,cus.getUserId());
            n = pre.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return n;
    }

    public static void main(String[] args) {
        DAOCustomer dao = new DAOCustomer();
        Customer cus = dao.searchbyEmail("ngocdbhe182383@fpt.edu.vn").get(0);
        cus.setRoleId("5");
        int n = dao.updateUser(cus);
        if (n > 0) {
            System.out.println(cus.getRoleId());
        }
    }
}