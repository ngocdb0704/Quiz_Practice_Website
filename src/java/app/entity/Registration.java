/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author admin
 */
public class Registration {
    private int id;
    private String subject;
    private String registrationTime;
    private int subjectPackage;
    private float totalCost;
    private String status;
    private String validFrom;
    private String validTo;

    public Registration() {
    }

    public Registration(int id, String subject, String registrationTime, int subjectPackage, float totalCost, String status, String validFrom, String validTo) {
        this.id = id;
        this.subject = subject;
        this.registrationTime = registrationTime;
        this.subjectPackage = subjectPackage;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    @Override
    public String toString() {
        return "Registration{" + "id=" + id + ", subject=" + subject + ", registrationTime=" + registrationTime + ", subjectPackage=" + subjectPackage + ", totalCost=" + totalCost + ", status=" + status + ", validFrom=" + validFrom + ", validTo=" + validTo + '}';
    }

    
    public void setId(int id) {
        this.id = id;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setRegistrationTime(String registrationTime) {
        this.registrationTime = registrationTime;
    }

    public void setSubjectPackage(int subjectPackage) {
        this.subjectPackage = subjectPackage;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getRegistrationTime() {
        return registrationTime;
    }

    public int getSubjectPackage() {
        return subjectPackage;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getValidTo() {
        return validTo;
    }
    
}
