package app.entity;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class BlogInformation {
    private int blogId;
    private int authorId;
    private String authorName;
    private int categoryId;
    private String categoryName;
    private String blogTitle;
    private Timestamp updatedTime;
    private String postBrief;

    public BlogInformation(ResultSet rs) throws SQLException {
        blogId = rs.getInt("BlogId");
        authorId = rs.getInt("UserId");
        authorName = rs.getString("FullName");
        categoryId = rs.getInt("BlogCategoryId");
        categoryName = rs.getString("BlogCategoryName");
        blogTitle = rs.getString("BlogTitle");
        updatedTime = rs.getTimestamp("UpdatedTime");
        postBrief = rs.getString("PostBrief");
    }

    public int getBlogId() {
        return blogId;
    }

    public String getAuthorFullName() {
        return authorName;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int id) {
        this.categoryId = id;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getPostBrief() {
        return postBrief;
    }

    public void setPostBrief(String postBrief) {
        this.postBrief = postBrief;
    }
}
