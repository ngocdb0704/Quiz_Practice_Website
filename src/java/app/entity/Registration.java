package app.entity;

import java.util.Date;

public class Registration {
    private int registrationId;
    private int userId;
    private String subjectId;
    private Date registrationTime;
    private int packageId;
    private float totalCost;
    private String status;
    private Date validFrom;
    private Date validTo;

    public Registration() {}

    public Registration(int registrationId, int userId, String subjectId, Date registrationTime, int packageId, float totalCost, String status, Date validFrom, Date validTo) {
        this.registrationId = registrationId;
        this.userId = userId;
        this.subjectId = subjectId;
        this.registrationTime = registrationTime;
        this.packageId = packageId;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }
}
