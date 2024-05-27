package app.dal;

import app.entity.BlogInformation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOBlog extends DBContext {

    public class QueryResult {
        private int total;
        private List<BlogInformation> results;

        public QueryResult(int total, List<BlogInformation> results) {
            this.total = total;
            this.results = results;
        }

        public int getTotal() {
            return total;
        }

        public List<BlogInformation> getResults() {
            return results;
        }
    }

    private static final String LISTING_QUERY = "select\n"
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
                + "inner join [User] u on u.UserId = b.UserId\n";

    private static final String COUNT_LISTING_QUERY = "select count(b.BlogId)\n"
                + "from [Blog] b\n"
                + "inner join [BlogCategory] c on b.BlogCategoryId = c.BlogCategoryId\n"
                + "where (? is null or b.[BlogTitle] = ?) and\n"
                + "(? = -1 or c.[BlogCategoryId] = ?)";

    private static final String FILTERED_QUERY = LISTING_QUERY
                + "where (? is null or b.[BlogTitle] = ?) and\n"
                + "(? = -1 or c.[BlogCategoryId] = ?)\n"
                + "order by b.[UpdatedTime] desc\n";

    /**
     * Download blog listings paginated
     * Blog listing is metadata of a blog, without including the post text
     * @param page - Page number starts at one
     * @param pageSize - Page size at least one
     * @return
     */
    public QueryResult getBlogListingsPaginated(int page, int pageSize) {
        return searchBlogListingsPaginated(null, -1, page, pageSize);
    }

    /**
     * Download blog listings paginated by category and title term
     * Blog listing is metadata of a blog, without including the post text
     * @param query - Blog title keyword to search
     * @param categoryId - Finds all blogs that is in this category, -1 for all categories
     * @param page - Page number starts at one
     * @param pageSize - Page size at least one
     * @return
     */
    public QueryResult searchBlogListingsPaginated(String query, int categoryId, int page, int pageSize) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 1;

        String sql = FILTERED_QUERY +
                "offset ? rows fetch next ? rows only";

        String likeStatement = (query == null) ? null : "%" + query + "%";

        try {
            PreparedStatement queryStmt = connection.prepareStatement(sql);

            int offset = (page - 1) * pageSize; //because we define page to start at one however sql server expects 0 offset
            
            queryStmt.setString(1, likeStatement);
            queryStmt.setString(2, likeStatement);
            queryStmt.setInt(3, categoryId);
            queryStmt.setInt(4, categoryId);
            queryStmt.setInt(5, offset);
            queryStmt.setInt(6, pageSize);
            ResultSet rs = queryStmt.executeQuery();

            List<BlogInformation> blogs = new ArrayList<>();
            while (rs.next()) {
                blogs.add(new BlogInformation(rs));
            }

            PreparedStatement countStmt = connection.prepareStatement(COUNT_LISTING_QUERY);
            countStmt.setString(1, likeStatement);
            countStmt.setString(2, likeStatement);
            countStmt.setInt(3, categoryId);
            countStmt.setInt(4, categoryId);
            ResultSet countRs = countStmt.executeQuery();

            int count = 0;
            if (countRs.next()) {
                count = countRs.getInt(1);
            }

            return new QueryResult(count, blogs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new QueryResult(0, new ArrayList<>());
    }
}
