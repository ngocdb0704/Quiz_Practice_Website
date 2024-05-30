/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author admin
 */
public class SubjectCategory {

    private int cateId;
    private String cateName;
    private int cateParentId;

    public SubjectCategory() {
    }

    public SubjectCategory(int cateId, String cateName, int cateParentId) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.cateParentId = cateParentId;
    }

    public int getCateId() {
        return cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public int getCateParentId() {
        return cateParentId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public void setCateParentId(int cateParentId) {
        this.cateParentId = cateParentId;
    }

}
