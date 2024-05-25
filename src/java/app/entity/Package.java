/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author admin
 */
public class Package {

    private int packageId;
    private String packageName;
    private float salePrice;

    public Package() {
    }

    public Package(int packageId, String packageName, float salePrice) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.salePrice = salePrice;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

}
