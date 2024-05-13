package app.dal;

import java.sql.*;
import app.model.User;

public class UserDAO extends DBContext {
    public boolean exists(String email) throws SQLException {
        User user = new User();

        PreparedStatement stmt = connection.prepareStatement("select email from Users where email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        return rs.next();
    }
}
