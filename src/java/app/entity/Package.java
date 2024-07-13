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
    private float listPrice;
    private float salePrice;
    private int duration;
    private boolean active;
    private String description = "";
    private int worth;

    public Package() {
    }

    public Package(int packageId, String packageName, float salePrice) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.salePrice = salePrice;
    }

    public Package(int packageId, String packageName, float listPrice, float salePrice) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
    }

    public Package(int packageId, String packageName, float listPrice, float salePrice, int duration, boolean active, int worth) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.listPrice = listPrice;
        this.salePrice = salePrice;
        this.duration = duration;
        this.active = active;
        this.worth = worth;
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

    public float getListPrice() {
        return listPrice;
    }

    public void setListPrice(float listPrice) {
        this.listPrice = listPrice;
    }

    public int getSalePriceVND() {
        return (int) (this.salePrice * 1000);
    }

    public int getListPriceVND() {
        return (int) (this.listPrice * 1000);
    }

    public void setListPriceVND(int listPrice) {
        this.listPrice = (float) listPrice / 1000;
    }

    public void applySale(int percentage) {
        if (percentage < 0) {
            percentage = 0;
        } else if (percentage > 100) {
            percentage = 100;
        }

        percentage = 100 - percentage;

        this.salePrice = ((float) percentage / 100) * this.listPrice;
    }

    public int getSalePercent() {
        if (listPrice == 0) {
            return 0;
        }

        return Math.round((1 - (salePrice / listPrice)) * 100);
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Package{" + "packageId=" + packageId + ", packageName=" + packageName + ", listPrice=" + listPrice + ", salePrice=" + salePrice + ", duration=" + duration + ", active=" + active + '}';
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration < 1) {
            duration = 1;
        }
        if (duration > 48) {
            duration = 48;
        }
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public boolean isValid() {
        boolean nameValid = this.packageName != null && !this.packageName.isBlank();
        boolean durationValid = this.duration >= 1 && this.duration <= 48;
        boolean listPriceValid = this.listPrice >= 1 && this.listPrice <= 2000;
        boolean salePriceValid = this.salePrice <= this.listPrice;
        return nameValid && durationValid && listPriceValid && salePriceValid;
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }
}
