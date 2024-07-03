package app.entity;

import java.util.Date;

public class Registration {

    private int registrationId;
    private String subjectName;
    private Date registrationTime;
    private String packageName;
    private float totalCost;
    private String status;
    private Date validFrom;
    private Date validTo;
    private String subjectImg;
    private int subjectId;

    public Registration() {
    }

    public Registration(int registrationId, String subjectName, Date registrationTime, String packageName, float totalCost, String status, Date validFrom, Date validTo, String subjectImg, int subjectId) {
        this.registrationId = registrationId;
        this.subjectName = subjectName;
        this.registrationTime = registrationTime;
        this.packageName = packageName;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.subjectImg = subjectImg;
        this.subjectId = subjectId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public String getSubjectImg() {
        return subjectImg;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public void setSubjectImg(String subjectImg) {
        this.subjectImg = subjectImg;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

}
