/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

import java.util.Date;

/**
 *
 * @author admin
 */
public class OrganizationRegistration {

    private String orgPackageName;
    private int subjectId;
    private String subjectTitle;
    private String subjectThumbnail;
    private Date validFrom;
    private Date validTo;

    public OrganizationRegistration() {
    }

    public OrganizationRegistration(String orgPackageName, int subjectId, String subjectTitle, String subjectThumbnail, Date validFrom, Date validTo) {
        this.orgPackageName = orgPackageName;
        this.subjectId = subjectId;
        this.subjectTitle = subjectTitle;
        this.subjectThumbnail = subjectThumbnail;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public String getOrgPackageName() {
        return orgPackageName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public String getSubjectThumbnail() {
        return subjectThumbnail;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setOrgPackageName(String orgPackageName) {
        this.orgPackageName = orgPackageName;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public void setSubjectThumbnail(String subjectThumbnail) {
        this.subjectThumbnail = subjectThumbnail;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

}
