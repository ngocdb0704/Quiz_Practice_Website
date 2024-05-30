/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author Administrator
 */
public class Customer {

    private int userId;
    private String email;
    private String password;
    private String roleid;
    private String fullName;
    private String genderid;
    private String mobile;
    private boolean isActive;


    public Customer() {
    }
    
    public Customer(int userId, String email, String password, String role, String fullName, String gender, String mobile, boolean isActive) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.roleid = role;
        this.fullName = fullName;
        this.genderid = gender;
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

    public String getRoleId() {
        return roleid;
    }

    public void setRoleId(String roleid) {
        this.roleid = roleid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGenderId() {
        return genderid;
    }

    public void setGenderId(String genderid) {
        this.genderid = genderid;
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

    @Override
    public String toString() {
        return "Customer{" + "userId=" + userId + ", email=" + email + ", password=" + password + ", roleid=" + roleid + ", fullName=" + fullName + ", genderid=" + genderid + ", mobile=" + mobile + ", isActive=" + isActive + '}';
    }

   

    
}
