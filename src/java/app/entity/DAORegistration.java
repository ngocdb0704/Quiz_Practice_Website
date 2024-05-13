/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

import java.sql.Statement;
import java.util.Vector;
import app.dal.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author admin
 */
public class DAORegistration extends DBContext{
    public Vector<Registration> getAll(String sql){
        Vector<Registration> vector = new Vector<>();
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt(1);
                String subject = rs.getString(2);
                String registrationTime = rs.getString(3);
                int subjectPackage = rs.getInt(4);
                float totalCost = rs.getFloat(5);
                String status = rs.getString(6);
                String validFrom = rs.getString(7);
                String validTo = rs.getString(8);
                Registration var = new Registration(id, subject, registrationTime, subjectPackage, totalCost, status, validFrom, validTo);
                vector.add(var);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vector;
    }
    public int removeRegistration(int id){
        int n=0;
        String sqlRemove = "DELETE FROM Registration WHERE Id = "+id;
        try {
            Statement state = connection.createStatement();
            n = state.executeUpdate(sqlRemove);
        } catch (SQLException ex) {
            Logger.getLogger(DAORegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return n;
    }
    public static void main(String[] args) {
        DAORegistration dao = new DAORegistration();
        int n = dao.removeRegistration(6);
//        Vector<Registration> vector = dao.getAll("Select * from [My Registration]");
//        for(Registration rs:vector){
//            System.out.println(rs.toString());
//        }
        if(n>0) System.out.println("removed");
    }
}
