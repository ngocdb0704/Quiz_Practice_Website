package app.entity;

public class ProfilePicture {
    private int pictureId;
    private int userId;
    private byte[] image;

    public ProfilePicture() {}

    public ProfilePicture(int pictureId, int userId, byte[] image) {
        this.pictureId = pictureId;
        this.userId = userId;
        this.image = image;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
