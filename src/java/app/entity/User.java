/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author OwO
 *
 */
public class User {

    private int userId;
    private String email;
    private String password;
    private String role;
    private String fullName;
    private String gender;
    private String mobile;
    private boolean isActive;
    private int roleId;
    private int genderId;

    public User() {
    }

    //Uses String gender and role
    public User(int userId, String email, String password, String role, String fullName, String gender, String mobile, boolean isActive) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.gender = gender;
        this.mobile = mobile;
        this.isActive = isActive;
    }

    //Uses genderId and roleId
    public User(int userId, String email, String password, int roleId, String fullName, int genderId, String mobile, boolean isActive) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.fullName = fullName;
        this.genderId = genderId;
        this.mobile = mobile;
        this.isActive = isActive;
    }

    public User(String email, String password, int roleId, String fullName, int genderId, String mobile, boolean isActive) {
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.fullName = fullName;
        this.genderId = genderId;
        this.mobile = mobile;
        this.isActive = isActive;
    }

    
    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

}
