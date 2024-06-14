package app.dal;

import app.entity.BlogInformation;
import app.entity.Blog;
import app.entity.MarkdownDocument;
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
import app.dal.QueryBuilder.Operator;
import app.dal.QueryBuilder.OrderDirection;

public class DAOBlog extends DBContext {

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
            + "inner join [BlogCategory] c on b.BlogCategoryId = c.BlogCategoryId\n";

    public MarkdownDocument getPostTextById(int id) {
        String sql = "select PostText from [Blog] where BlogId = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new MarkdownDocument(rs.getString("PostText"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    public BlogInformation getBlogInformationById(int id) {
        String sql = LISTING_QUERY
                + "where b.BlogId = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new BlogInformation(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }

    public QueryResult getByCategory(int categoryId, int limit) {
        return searchBlogListingsPaginated(null, categoryId, null, null, 1, limit);
    }

    public QueryResult getRecentBlogs(int limit) {
        return searchBlogListingsPaginated(null, -1, null, null, 1, limit);
    }

    /**
     * Download blog listings paginated by category and title term Blog listing
     * is metadata of a blog, without including the post text
     *
     * @param query - Blog title keyword to search
     * @param categoryId - Finds all blogs that is in this category, -1 for all
     * categories
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
        try {
            QueryBuilder searchQuery = new QueryBuilder(LISTING_QUERY);
            searchQuery.setLoggingEnabled(true);

            if (categoryId != -1) {
                searchQuery.where("b.BlogCategoryId", Operator.EQUALS, categoryId);
            }

            if (query != null && !query.isBlank()) {
                searchQuery.where("b.BlogTitle", Operator.LIKE, "%" + query + "%");
            }

            if (startDate != null && endDate != null) {
                LocalDateTime s = startDate.atTime(0, 0, 0);
                LocalDateTime e = endDate.atTime(23, 59, 59);
                Timestamp startTimestamp = Timestamp.from(s.toInstant(ZoneOffset.UTC));
                Timestamp endTimestamp = Timestamp.from(e.toInstant(ZoneOffset.UTC));

                searchQuery.where("b.UpdatedTime", Operator.BETWEEN, startTimestamp, endTimestamp);
            }

            searchQuery
                    .orderBy("b.UpdatedTime", OrderDirection.DESC)
                    .page(page, pageSize);

            ResultSet rs = searchQuery.toPreparedStatement(connection).executeQuery();

            List<BlogInformation> blogs = new ArrayList<>();
            while (rs.next()) {
                blogs.add(new BlogInformation(rs));
            }

            QueryBuilder countQuery = new QueryBuilder(COUNT_LISTING_QUERY, searchQuery);
            countQuery.setLoggingEnabled(true);

            ResultSet countRs = countQuery.toPreparedStatement(connection).executeQuery();

            int count = 0;
            if (countRs.next()) {
                count = countRs.getInt(1);
            }

            return new QueryResult(count, pageSize, blogs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new QueryResult(0, 0, new ArrayList<>());
    }

    //Hard-coded ids
    //TODO: Make an algorithm to define what posts are considered as "hot"
    static int[] ListOfHotPosts = {1, 8, 2, 4, 10, 3, 6, 7, 9, 46, 26, 25, 16, 38, 44, 50, 31, 27, 39, 40, 49, 18, 42, 34};

    public List<Blog> getEnoughToDisplay(int ammout, int offSet) {
        List<Blog> Out = new ArrayList<>();
        System.out.println(Arrays.stream(Arrays.copyOfRange(ListOfHotPosts, offSet, Math.min(offSet + ammout, ListOfHotPosts.length)))
                .mapToObj(Integer::toString).collect(Collectors.joining(",")));
        String sql = "SELECT TOP (?) BlogId, UserId, BlogCategoryId, BlogTitle, UpdatedTime, PostBrief FROM Blog WHERE BlogId IN ("
                + Arrays.stream(Arrays.copyOfRange(ListOfHotPosts, offSet, Math.min(offSet + ammout, ListOfHotPosts.length)))
                        .mapToObj(Integer::toString).collect(Collectors.joining(","))
                + ")";
        System.out.println(sql);

        PreparedStatement pre;
        try {
            pre = connection.prepareStatement(sql);
            pre.setInt(1, ammout);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                Blog a = new Blog(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6));
                Out.add(a);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return Out;
    }

    public static void main(String[] args) {
        DAOBlog test = new DAOBlog();
        System.out.println(test.getEnoughToDisplay(3, 3));
    }
}
