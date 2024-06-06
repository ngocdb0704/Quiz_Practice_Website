package app.dal;

import app.entity.BlogCategory;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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
    
    public ConcurrentHashMap<Integer, String> getMap() {
        ConcurrentHashMap<Integer, String> Out = new ConcurrentHashMap<>();
        String sql = "select * from [BlogCategory]";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Out.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return Out;
    }
}
