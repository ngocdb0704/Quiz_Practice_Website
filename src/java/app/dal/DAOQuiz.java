package app.dal;

import static app.dal.QueryBuilder.Operator;
import static app.dal.QueryBuilder.OrderDirection;
import app.entity.BlogInformation;
import java.sql.*;

public class DAOQuiz extends DBContext {
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

    public void get() throws Exception {
        PreparedStatement stmt = new QueryBuilder(LISTING_QUERY)
                .where("b.BlogTitle", Operator.LIKE, "%Top%")
                .toPreparedStatement(connection);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println(new BlogInformation(rs));
        }
    }
    
    public static void main(String[] args) throws Exception {
        new DAOQuiz().get();
    }
}
