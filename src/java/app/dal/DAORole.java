package app.dal;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAORole extends DBContext {

    //Using ConcurrentHashMap (similar to Hashtable) instead of Hashmap because it is safer for threaded operations.
    public ConcurrentHashMap<Integer, String> getMap() {
        ConcurrentHashMap<Integer, String> Out = new ConcurrentHashMap<>();
        String sql = "select * from Role";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Out.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }
    
    //In case you only know role name
    public ConcurrentHashMap<String, Integer> getReversedMap() {
        ConcurrentHashMap<String, Integer> Out = new ConcurrentHashMap<>();
        String sql = "select * from Role";
        try {
            Statement state = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()) {
                Out.put(rs.getString(2), rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }
    
    public static void main(String[] args) {
        System.out.println(new DAORole().getMap());
        System.out.println(new DAORole().getMap().get(1));
    }
}
