/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author admin
 */
public class Organization {
    private int orgId;
    private String orgName;

    public Organization() {
    }

    public Organization(int orgId, String orgName) {
        this.orgId = orgId;
        this.orgName = orgName;
    }

    public int getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
}
