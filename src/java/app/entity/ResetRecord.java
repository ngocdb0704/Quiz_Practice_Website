package app.entity;

import java.sql.Timestamp;
import java.time.Instant;

public class ResetRecord {
    private int userId;
    private String token;
    private Timestamp validTo;

    public ResetRecord() {
    }

    public ResetRecord(int userId, String token, Timestamp validTo) {
        this.userId = userId;
        this.token = token;
        this.validTo = validTo;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getValidTo() {
        return validTo;
    }

    public void setValidTo(Timestamp validTo) {
        this.validTo = validTo;
    }

    public boolean isValid() {
        return Instant.now().isBefore(this.validTo.toInstant());
    }
}
