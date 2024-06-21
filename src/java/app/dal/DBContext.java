package app.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext implements AutoCloseable {

    public Connection connection;

    public DBContext() {
        try {
            //Change the username password and url to connect your own database
            String username = "sa"; 
            String password = "123";
            String url = "jdbc:sqlserver://localhost:1433;databaseName=Quiz_Practice";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(true);
            Logger.getLogger(DBContext.class.getName()).log(Level.INFO, "Creating connection");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null) {
                connection.close();
                Logger.getLogger(DBContext.class.getName()).log(Level.INFO, "Closing connection");
            }
        } catch (Exception ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Failed to close connection", ex);
        }
    }
}


