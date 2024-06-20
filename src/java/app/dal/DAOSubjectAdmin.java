package app.dal;

import app.entity.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static app.dal.QueryBuilder.Operator;
import static app.dal.QueryBuilder.OrderDirection;

public class DAOSubjectAdmin extends DBContext {

    /**
     *
     * @param title - Subject title
     * @param order
     * @return
     */
    public QueryResult search(String title, boolean asc, int page, int pageSize) {
        List<Subject> ret = new ArrayList<>();
        int count = 0;

        try {
            String sql = "SELECT SubjectId, SubjectTitle, SubjectTagLine, SubjectBriefInfo, SubjectDescription, SubjectThumbnail, SubjectCategoryId FROM Subject";


            QueryBuilder query = new QueryBuilder(sql);

            if (title != null && !title.isBlank()) {
                query.whereAnd("SubjectTitle", Operator.LIKE, "%" + title + "%");
            }

            query.orderBy("SubjectCreatedDate", asc ? OrderDirection.ASC : OrderDirection.DESC);
            query.orderBy("SubjectUpdatedDate", asc ? OrderDirection.ASC : OrderDirection.DESC);
            query.page(page, pageSize);
            ResultSet rs = query.toPreparedStatement(connection).executeQuery();

            while (rs.next()) {
                Subject sub = new Subject(
                        rs.getInt(1), 
                        rs.getString(2), 
                        rs.getString(3), 
                        rs.getString(4), 
                        rs.getString(5), 
                        rs.getString(6),
                        rs.getInt(7)
                );
                
                ret.add(sub);
            }
            
            rs = new QueryBuilder("select count(*) from Subject", query)
                    .toPreparedStatement(connection)
                    .executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new QueryResult<Subject>(count, pageSize, ret);
    }
}
