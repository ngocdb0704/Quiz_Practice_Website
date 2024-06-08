package app.dal;

import app.entity.BlogInformation;
import app.entity.Blog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DAOBlog extends DBContext {

    public class QueryResult {
        private int totalItems;
        private int pageSize;
        private List<BlogInformation> results;

        public QueryResult(int total, int pageSize, List<BlogInformation> results) {
            this.totalItems = total;
            this.pageSize = pageSize;
            this.results = results;
        }

        public List<BlogInformation> getResults() {
            return results;
        }

        public int getTotalPages() {
            if (pageSize > 0) {
                return (int)(Math.ceil((double)totalItems / pageSize));
            }

            return 0;
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
                + "where (? is NULL or b.[BlogTitle] LIKE ?) and\n"
                + "(? = -1 or c.[BlogCategoryId] = ?) and \n"
                + "((? is NULL or ? is NULL) or (b.[UpdatedTime] between ? and ?))";

    private static final String FILTERED_QUERY = LISTING_QUERY
                + "where (? is NULL or b.[BlogTitle] LIKE ?) and\n"
                + "(? = -1 or c.[BlogCategoryId] = ?) and \n"
                + "((? is NULL or ? is NULL) or (b.[UpdatedTime] between ? and ?))\n"
                + "order by b.[UpdatedTime] desc\n";

    /**
     * Download blog listings paginated by category and title term
     * Blog listing is metadata of a blog, without including the post text
     * @param query - Blog title keyword to search
     * @param categoryId - Finds all blogs that is in this category, -1 for all categories
     * @param startDate - Start of updated time range
     * @param endDate - End of updated time range
     * @param page - Page number starts at one
     * @param pageSize - Page size at least one
     * @return
     */
    public QueryResult searchBlogListingsPaginated(
            String query, int categoryId,
            LocalDate startDate, LocalDate endDate,
            int page, int pageSize
    ) {
        if (page < 1) page = 1;
        if (pageSize < 1) pageSize = 1;

        String sql = FILTERED_QUERY +
                "offset ? rows fetch next ? rows only";

        String likeStatement = (query == null) ? null : "%" + query + "%";

        try {
            PreparedStatement queryStmt = connection.prepareStatement(sql);
           
            //because we define page to start at one however sql server expects 0 offset
            int offset = (page - 1) * pageSize;
            
            Timestamp startTimestamp = null;
            Timestamp endTimestamp = null;
            
            if (startDate != null && endDate != null) {
                LocalDateTime s = startDate.atTime(0, 0, 0);
                LocalDateTime e = endDate.atTime(23, 59, 59);
                startTimestamp = Timestamp.from(s.toInstant(ZoneOffset.UTC));
                endTimestamp = Timestamp.from(e.toInstant(ZoneOffset.UTC));
            }
            
            queryStmt.setString(1, likeStatement);
            queryStmt.setString(2, likeStatement);
            queryStmt.setInt(3, categoryId);
            queryStmt.setInt(4, categoryId);
            queryStmt.setTimestamp(5, startTimestamp);
            queryStmt.setTimestamp(6, endTimestamp);
            queryStmt.setTimestamp(7, startTimestamp);
            queryStmt.setTimestamp(8, endTimestamp);
            queryStmt.setInt(9, offset);
            queryStmt.setInt(10, pageSize);
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
            countStmt.setTimestamp(5, startTimestamp);
            countStmt.setTimestamp(6, endTimestamp);
            countStmt.setTimestamp(7, startTimestamp);
            countStmt.setTimestamp(8, endTimestamp);
            ResultSet countRs = countStmt.executeQuery();

            int count = 0;
            if (countRs.next()) {
                count = countRs.getInt(1);
            }

            return new QueryResult(count, pageSize, blogs);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new QueryResult(0, 0, new ArrayList<>());
    }
    
    //Hard-coded ids
    static int[] ListOfHotPosts = {1, 3, 4, 5};
    
    public List<Blog> getEnoughToDisplay(int ammout, int offSet) {
        List<Blog> Out = new ArrayList<>();
        String sql = "SELECT BlogId, UserId, BlogCategoryId, BlogTitle, UpdatedTime, PostText FROM Blog WHERE BlogId IN ("
                + Arrays.stream(ListOfHotPosts).mapToObj(Integer::toString).collect(Collectors.joining(","))
                + ") ORDER BY BlogId DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        
        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, offSet);
            pre.setInt(2, ammout);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Blog a = new Blog(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
                Out.add(a);
            }
        } catch (SQLException ex) {System.out.println(ex);}
        return Out;
    }
    
    public static void main(String[] args) {
        DAOBlog test = new DAOBlog();
        System.out.println(test.getEnoughToDisplay(3, 1));
    }
}
