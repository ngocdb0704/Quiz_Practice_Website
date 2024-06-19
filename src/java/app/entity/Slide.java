/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author OwO
 */
public class Slide {
    private Integer slideId;
    private String title;
    private String imageRef; // Use String for image reference
    private byte[] image; // Keep byte[] for byte data if needed
    private String backlink;
    private String status; // Use String for status
    private Integer userId;

    public Slide() {
    }

    public Slide(Integer slideId, String title, String imageRef, String backlink, String status, Integer userId) {
        this.slideId = slideId;
        this.title = title;
        this.imageRef = imageRef;
        this.backlink = backlink;
        this.status = status;
        this.userId = userId;
    }

    public Slide(Integer slideId, String title, String imageRef, String backlink, String status) {
        this.slideId = slideId;
        this.title = title;
        this.imageRef = imageRef;
        this.backlink = backlink;
        this.status = status;
    }

    public Integer getSlideId() {
        return slideId;
    }

    public void setSlideId(Integer slideId) {
        this.slideId = slideId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBacklink() {
        return backlink;
    }

    public void setBacklink(String backlink) {
        this.backlink = backlink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
}
