package app.dal;

import java.sql.*;
import app.entity.User;
import java.util.ArrayList;
import java.util.List;

public class DAOUser extends DBContext {
    public List<User> extractResults(ResultSet rs) throws SQLException {
        List<User> result = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("UserId"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Password"));
            user.setFullName(rs.getString("FullName"));
            user.setGender(rs.getString("Gender"));
            user.setMobile(rs.getString("Mobile"));
            user.setRole(rs.getString("Role"));
            user.setIsActive(rs.getBoolean("IsActive"));

            result.add(user);
        }

        return result;
    }

    public User getByEmail(String email) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from [User] where [Email] = ?");
        stmt.setString(1, email);
        List<User> result = extractResults(stmt.executeQuery());
        return result.isEmpty() ? null : result.get(0);
    }

    public User getById(int id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from [User] where [UserId] = ?");
        stmt.setInt(1, id);
        List<User> result = extractResults(stmt.executeQuery());
        return result.isEmpty() ? null : result.get(0);
    }

    public void updatePassword(int id, String password) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("update [User] set [Password] = ? where [UserId] = ?");
        stmt.setString(1, password);
        stmt.setInt(2, id);
        stmt.executeUpdate();
    }
}
