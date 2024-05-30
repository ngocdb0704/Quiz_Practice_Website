/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author nguye
 */
public class Slide {
    private Integer slideId;
    private String title;
    private byte[] image;
    private String imageRef;
    private String backlink;
    private String status;

    public Slide() {
    }
    
    public Slide(Integer sildeId, String title, byte[] image, String backlink, String status) {
        this.slideId = sildeId;
        this.title = title;
        this.image = image;
        this.backlink = backlink;
        this.status = status;
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

    public String getImageRef() {
        return imageRef;
    }

    public void setImageRef(String imageRef) {
        this.imageRef = imageRef;
    }

    @Override
    public String toString() {
        return "Slide{" + "slideId=" + slideId + ", title=" + title + ", image=" + image + ", imageRef=" + imageRef + ", backlink=" + backlink + ", status=" + status + '}';
    }
}
