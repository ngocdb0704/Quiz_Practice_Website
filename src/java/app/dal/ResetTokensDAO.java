package app.dal;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ResetTokensDAO extends DBContext {
    public String createForUserId(int userId) throws SQLException {
        deleteToken(userId);
        
        String token = UUID.randomUUID().toString();
        Instant expireDate = Instant.now().plus(10, ChronoUnit.MINUTES);
        String sql = "insert into ResetTokens (userId, token, validTo) values (?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setString(2, token);
        stmt.setTimestamp(3, Timestamp.from(expireDate));
        stmt.executeUpdate();

        return token;
    }

    public void deleteToken(int userId) throws SQLException {
        String sql = "delete from ResetTokens where userId = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }
}
