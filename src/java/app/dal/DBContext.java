package app.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    public Connection connection;

    public DBContext() {
        try {
            //Change the username password and url to connect your own database
            String username = "sa";
            String password = "123";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quiz_Practice";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() {
        if (connection != null){
            try {
                connection.close();
                System.out.println("Connection manually closed");
            }
            catch (Exception e) {}

        }
    }

    public static void main(String[] args) {
        DBContext test = new DBContext();
        System.out.println("Connection is ok");
        test.closeConnection();
    }

}
