package app.dal;

import app.entity.BlogInformation;
import app.entity.Blog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOBlog extends DBContext {

    /**
     * Download blog listings paginated
     * Blog listing is metadata of a blog, without including the post text
     * @param page - Page number starts at one
     * @param pageSize - Page size at least one
     * @return
     */
    public List<BlogInformation> getBlogListingsPaginated(int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 1;
        
        String sql = "select\n"
                + "b.BlogId,\n"
                + "b.BlogTitle,\n"
                + "b.PostBrief,\n"
                + "b.UpdatedTime,\n"
                + "u.FullName,\n"
                + "u.UserId,\n"
                + "c.BlogCategoryId,\n"
                + "c.BlogCategoryName\n"
                + "from [Blog] b\n"
                + "inner join [BlogCategory] c on b.BlogCategoryId = c.BlogCategoryId\n"
                + "inner join [User] u on u.UserId = b.UserId\n"
                + "order by b.[UpdatedTime] desc\n"
                + "offset ? rows fetch next ? rows only;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            int offset = (page - 1) * pageSize; //because we define page to start at one however sql server expects 0 offset
            
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();

            List<BlogInformation> blogs = new ArrayList<>();
            while (rs.next()) {
                blogs.add(new BlogInformation(rs));
            }

            return blogs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    public int getBlogCount() {
        try {
            PreparedStatement stmt = connection.prepareStatement("select count(BlogId) from [Blog]");
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0;
    }
    
    public List<Blog> getEnoughToDisplay(int ammoutOfBlogs) {
        List<Blog> Out = new ArrayList<>();
        String sql = "SELECT TOP (?) * FROM Blog";
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, ammoutOfBlogs);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Blog a = new Blog(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
                Out.add(a);
            }
        } catch (SQLException ex) {System.out.println(ex);}
        return Out;
    }
    
    public static void main(String[] args) {
        System.out.println(new DAOBlog().getEnoughToDisplay(3).size());
    }
}
