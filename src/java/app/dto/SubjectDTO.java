/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.dto;

/**
 *
 * @author OwO
 */

import java.util.Date;

public class SubjectDTO {
    private int subjectId;
    private String subjectTitle;
    private int subjectCategoryId;
    private int subjectStatus;
    private int subjectLevelId;
    private boolean isFeaturedSubject;
    private java.util.Date subjectCreatedDate;
    private java.util.Date subjectUpdatedDate;
    private String subjectTagLine;
    private String subjectBriefInfo;
    private String subjectDescription;
    private String subjectThumbnail; 

    public SubjectDTO() {
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public int getSubjectCategoryId() {
        return subjectCategoryId;
    }

    public void setSubjectCategoryId(int subjectCategoryId) {
        this.subjectCategoryId = subjectCategoryId;
    }

    public int getSubjectStatus() {
        return subjectStatus;
    }

    public void setSubjectStatus(int subjectStatus) {
        this.subjectStatus = subjectStatus;
    }

    public int getSubjectLevelId() {
        return subjectLevelId;
    }

    public void setSubjectLevelId(int subjectLevelId) {
        this.subjectLevelId = subjectLevelId;
    }

    public boolean isIsFeaturedSubject() {
        return isFeaturedSubject;
    }

    public void setIsFeaturedSubject(boolean isFeaturedSubject) {
        this.isFeaturedSubject = isFeaturedSubject;
    }

    public Date getSubjectCreatedDate() {
        return subjectCreatedDate;
    }

    public void setSubjectCreatedDate(Date subjectCreatedDate) {
        this.subjectCreatedDate = subjectCreatedDate;
    }

    public Date getSubjectUpdatedDate() {
        return subjectUpdatedDate;
    }

    public void setSubjectUpdatedDate(Date subjectUpdatedDate) {
        this.subjectUpdatedDate = subjectUpdatedDate;
    }

    public String getSubjectTagLine() {
        return subjectTagLine;
    }

    public void setSubjectTagLine(String subjectTagLine) {
        this.subjectTagLine = subjectTagLine;
    }

    public String getSubjectBriefInfo() {
        return subjectBriefInfo;
    }

    public void setSubjectBriefInfo(String subjectBriefInfo) {
        this.subjectBriefInfo = subjectBriefInfo;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public String getSubjectThumbnail() {
        return subjectThumbnail;
    }

    public void setSubjectThumbnail(String subjectThumbnail) {
        this.subjectThumbnail = subjectThumbnail;
    }

    @Override
    public String toString() {
        return "SubjectDTO{" + "subjectId=" + subjectId + ", subjectTitle=" + subjectTitle + ", subjectCategoryId=" + subjectCategoryId + ", subjectStatus=" + subjectStatus + ", subjectLevelId=" + subjectLevelId + ", isFeaturedSubject=" + isFeaturedSubject + ", subjectCreatedDate=" + subjectCreatedDate + ", subjectUpdatedDate=" + subjectUpdatedDate + ", subjectTagLine=" + subjectTagLine + ", subjectBriefInfo=" + subjectBriefInfo + ", subjectDescription=" + subjectDescription + ", subjectThumbnail=" + subjectThumbnail + '}';
    }
    
    
    
}
