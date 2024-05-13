/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author Administrator
 */
public class Customer {
    private int UserID;
    private String CustomerEmail;
    private String Password;
    private String Role;
    private String FullName;
    private String Gender;
    private String Mobile;

    public Customer() {
    }

    public Customer(int UserID, String CustomerEmail, String Password, String Role, String FullName, String Gender, String Mobile) {
        this.UserID = UserID;
        this.CustomerEmail = CustomerEmail;
        this.Password = Password;
        this.Role = Role;
        this.FullName = FullName;
        this.Gender = Gender;
        this.Mobile = Mobile;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String CustomerEmail) {
        this.CustomerEmail = CustomerEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    @Override
    public String toString() {
        return "Customer{" + "UserID=" + UserID + ", CustomerEmail=" + CustomerEmail + ", Password=" + Password + ", Role=" + Role + ", FullName=" + FullName + ", Gender=" + Gender + ", Mobile=" + Mobile + '}';
    }
    
}
