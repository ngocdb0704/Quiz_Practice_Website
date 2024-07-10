/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author admin
 */
public class Subject {

    private int subjectId;
    private String subjectName;
    private String tagLine;
    private String briefInfo;
    private String subjectDescription;
    private String thumbnail;
    private String lowestPackageName;
    private int categoryId;
    private float packageListPrice;
    private float packageSalePrice;
    private String level;
    private String provider;
    private String sponsorer;
    private boolean isFeatured;
    private int statusId;
    private int ownerId;

    public Subject() {
    }

    public Subject(int subjectId, String subjectName) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public Subject(int subjectId, String subjectName, String tagLine, String briefInfo, String subjectDescription, String thumbnail, int categoryId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.tagLine = tagLine;
        this.briefInfo = briefInfo;
        this.subjectDescription = subjectDescription;
        this.thumbnail = thumbnail;
        this.categoryId = categoryId;
    }

    public Subject(int subjectId, String subjectName, String tagLine, String briefInfo, String subjectDescription, String thumbnail, int categoryId,
             boolean isFeatured, int statusId, int ownerId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.tagLine = tagLine;
        this.briefInfo = briefInfo;
        this.subjectDescription = subjectDescription;
        this.thumbnail = thumbnail;
        this.isFeatured = isFeatured;
        this.statusId = statusId;
        this.ownerId = ownerId;
        this.categoryId = categoryId;
    }

    public Subject(int subjectId, String subjectName, String tagLine, String briefInfo, String subjectDescription, String thumbnail, String lowestPackageName, int categoryId, float packageListPrice, float packageSalePrice, String level, String provider, String sponsorer) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.tagLine = tagLine;
        this.briefInfo = briefInfo;
        this.subjectDescription = subjectDescription;
        this.thumbnail = thumbnail;
        this.lowestPackageName = lowestPackageName;
        this.categoryId = categoryId;
        this.packageListPrice = packageListPrice;
        this.packageSalePrice = packageSalePrice;
        this.level = level;
        this.provider = provider;
        this.sponsorer = sponsorer;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getLowestPackageName() {
        return lowestPackageName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public float getPackageListPrice() {
        return packageListPrice;
    }

    public float getPackageSalePrice() {
        return packageSalePrice;
    }

    public String getLevel() {
        return level;
    }

    public String getProvider() {
        return provider;
    }

    public String getSponsorer() {
        return sponsorer;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setLowestPackageName(String lowestPackageName) {
        this.lowestPackageName = lowestPackageName;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setPackageListPrice(float packageListPrice) {
        this.packageListPrice = packageListPrice;
    }

    public void setPackageSalePrice(float packageSalePrice) {
        this.packageSalePrice = packageSalePrice;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setSponsorer(String sponsorer) {
        this.sponsorer = sponsorer;
    }

    public boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Subject{" + "subjectId=" + subjectId + ", subjectName=" + subjectName + ", tagLine=" + tagLine + ", briefInfo=" + briefInfo + ", subjectDescription=" + subjectDescription + ", thumbnail=" + thumbnail + ", lowestPackageName=" + lowestPackageName + ", categoryId=" + categoryId + ", packageListPrice=" + packageListPrice + ", packageSalePrice=" + packageSalePrice + ", level=" + level + ", provider=" + provider + ", sponsorer=" + sponsorer + ", isFeatured=" + isFeatured + ", statusId=" + statusId + ", ownerId=" + ownerId + '}';
    }
}
