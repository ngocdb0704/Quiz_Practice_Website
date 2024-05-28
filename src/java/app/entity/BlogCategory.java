package app.entity;

import java.sql.SQLException;
import java.sql.ResultSet;

public class BlogCategory {
    private int categoryId;
    private String categoryName;
    
    public BlogCategory(ResultSet rs) throws SQLException {
        categoryId = rs.getInt("BlogCategoryId");
        categoryName = rs.getString("BlogCategoryName");
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
