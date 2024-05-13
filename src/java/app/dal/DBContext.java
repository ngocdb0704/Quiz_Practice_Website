package app.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    public Connection connection;

    
    public DBContext(String url, String user, String pass) {
        try {
            //Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //connection
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("connected");
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }

    public DBContext() {
        this("jdbc:sqlserver://localhost:1433;databaseName=Quiz_Practice", "sa", "123456");
    }

    public static void main(String[] args) {
        new DBContext();
    }
}
