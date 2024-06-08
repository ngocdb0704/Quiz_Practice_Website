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
    private String subjectCategory;
    private String tagLine;
    private String thumbnail;
    private String createdDate;
    private String updatedDate;
    private String lowestPackageName;
    private float packageListPrice;
    private float packageSalePrice;

    public Subject() {
    }

    public Subject(int subjectId, String subjectName, String subjectCategory, String tagLine, String thumbnail, String createdDate, String updatedDate, String lowestPackageName, float packageListPrice, float packageSalePrice) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectCategory = subjectCategory;
        this.tagLine = tagLine;
        this.thumbnail = thumbnail;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.lowestPackageName = lowestPackageName;
        this.packageListPrice = packageListPrice;
        this.packageSalePrice = packageSalePrice;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectCategory() {
        return subjectCategory;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getLowestPackageName() {
        return lowestPackageName;
    }

    public float getPackageListPrice() {
        return packageListPrice;
    }

    public float getPackageSalePrice() {
        return packageSalePrice;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setSubjectCategory(String subjectCategory) {
        this.subjectCategory = subjectCategory;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public void setLowestPackageName(String lowestPackageName) {
        this.lowestPackageName = lowestPackageName;
    }

    public void setPackageListPrice(float packageListPrice) {
        this.packageListPrice = packageListPrice;
    }

    public void setPackageSalePrice(float packageSalePrice) {
        this.packageSalePrice = packageSalePrice;
    }

}
