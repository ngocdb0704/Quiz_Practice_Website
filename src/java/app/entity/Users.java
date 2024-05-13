/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author quatn
 */
public class Users {
    private Integer Id ;
    private String Email, Password, Role, FullName, Gender, Mobile;

    public Users() {
    }
  
    public Users(Integer Id, String Email, String Password, String Role, String FullName, String Gender, String Mobile) {
        this.Id = Id;
        this.Email = Email;
        this.Password = Password;
        this.Role = Role;
        this.FullName = FullName;
        this.Gender = Gender;
        this.Mobile = Mobile;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
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
        return "Users{" + "Id=" + Id + ", Email=" + Email + ", Password=" + Password + ", Role=" + Role + ", FullName=" + FullName + ", Gender=" + Gender + ", Mobile=" + Mobile + '}';
    }
}
