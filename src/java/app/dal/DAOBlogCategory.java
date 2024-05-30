package app.dal;

import app.entity.BlogCategory;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOBlogCategory extends DBContext {
    private static final String CATEGORY_LISTING_QUERY = "select * from [BlogCategory]";
    
    public List<BlogCategory> getAllCategories() {
        List<BlogCategory> categories = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(CATEGORY_LISTING_QUERY);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                categories.add(new BlogCategory(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
            
        return categories;
    }
}
