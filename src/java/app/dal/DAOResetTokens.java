package app.dal;

import app.entity.ResetRecord;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DAOResetTokens extends __local__DBContext {
    public List<ResetRecord> extractResults(ResultSet rs) throws SQLException {
        List<ResetRecord> result = new ArrayList<>();

        while (rs.next()) {
            ResetRecord resetRecord = new ResetRecord();
            resetRecord.setUserId(rs.getInt("UserId"));
            resetRecord.setToken(rs.getString("Token"));
            resetRecord.setValidTo(rs.getTimestamp("ValidTo"));

            result.add(resetRecord);
        }

        return result;
    }
    
    public ResetRecord getByToken(String token) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("select * from [ResetToken] where [Token] = ?");
        stmt.setString(1, token);
        List<ResetRecord> result = extractResults(stmt.executeQuery());

        return result.isEmpty() ? null : result.get(0);
    }
    
    public String createForUserId(int userId) throws SQLException {
        deleteToken(userId);
        
        String token = UUID.randomUUID().toString();
        Instant expireDate = Instant.now().plus(10, ChronoUnit.MINUTES);
        String sql = "insert into [ResetToken] ([UserId], [Token], [ValidTo]) values (?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setString(2, token);
        stmt.setTimestamp(3, Timestamp.from(expireDate));
        stmt.executeUpdate();

        return token;
    }

    public void deleteToken(int userId) throws SQLException {
        String sql = "delete from [ResetToken] where [UserId] = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.executeUpdate();
    }
}
