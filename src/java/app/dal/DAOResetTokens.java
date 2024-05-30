package app.dal;

import app.entity.ResetRecord;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DAOResetTokens extends DBContext {
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
    
    public ResetRecord getByToken(String token) {
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from [ResetToken] where [Token] = ?");
            stmt.setString(1, token);
            List<ResetRecord> result = extractResults(stmt.executeQuery());

            return result.isEmpty() ? null : result.get(0);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public String createForUserId(int userId, int minutes) {
        if (minutes < 1) throw new IllegalArgumentException("Time must be at least 1 minute");

        boolean success = deleteToken(userId);
        if (!success) {
            return null;
        }
        
        String token = UUID.randomUUID().toString();
        Instant expireDate = Instant.now().plus(minutes, ChronoUnit.MINUTES);
        String sql = "insert into [ResetToken] ([UserId], [Token], [ValidTo]) values (?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, token);
            stmt.setTimestamp(3, Timestamp.from(expireDate));
            stmt.executeUpdate();

            return token;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean deleteToken(int userId) {
        String sql = "delete from [ResetToken] where [UserId] = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
