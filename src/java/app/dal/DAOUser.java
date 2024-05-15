package app.dal;

import java.sql.*;
import app.entity.User;

public class DAOUser extends DBContext {
    public User getByEmail(String email) throws SQLException {
        User user = new User();

        PreparedStatement stmt = connection.prepareStatement("select * from Users where email = ?");
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("fullName"));
            user.setGender(rs.getString("gender"));
            user.setMobile(rs.getString("mobile"));

            return user;
        }

        return null;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "update Users "
                + "email = ?, "
                + "password = ?, "
                + "fullName = ?, "
                + "gender = ?, "
                + "mobile = ? "
                + "where email = ?";

        PreparedStatement stmt = connection.prepareStatement("select * from Users where email = ?");
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getFullName());
        stmt.setString(4, user.getGender());
        stmt.setString(5, user.getMobile());

        stmt.executeUpdate();
    }
}
