/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author OwO
 * Modified by QuanNM @3pm May 25th: Change to new RoleId and GenderId to adhere to the database update, 
 *      please use DAOGender and DAORole to get mapping from Id to Value from now on.
 * Modified by QuanNM @8:30AM May 26th: Added back string role and gender to avoid conflict with old code
 */

public class User {
    private int userId;
    private String email;
    private String password;
    private int roleId;
    private String fullName;
    private int genderId;
    private String mobile;
    private boolean isActive;

    public User() {}

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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
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


    
}
