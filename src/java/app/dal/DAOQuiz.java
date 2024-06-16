package app.dal;

import static app.dal.QueryBuilder.Operator;
import static app.dal.QueryBuilder.OrderDirection;
import app.entity.BlogInformation;
import app.entity.QuizInformation;
import app.entity.QuizType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOQuiz extends DBContext {
    private static final String LISTING_QUERY =
            "select q.*, s.SubjectTitle from [Quiz] q\n"
            + "inner join [Subject] s on q.SubjectId = s.SubjectId";

    private static final String COUNT_LISTING_QUERY =
            "select count(*) from [Quiz] q\n"
            + "inner join [Subject] s on q.SubjectId = s.SubjectId";

    public void markDraft(Integer[] ids) {
        try {
            new QueryBuilder("update [Quiz] set IsPublished = 0")
                .where("QuizId", Operator.IN, ids)
                .toPreparedStatement(connection)
                .executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void publish(Integer[] ids) {
        try {
            new QueryBuilder("update [Quiz] set IsPublished = 1")
                .where("QuizId", Operator.IN, ids)
                .toPreparedStatement(connection)
                .executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(Integer[] ids) {
        try {
            new QueryBuilder("delete from [Quiz]")
                .where("QuizId", Operator.IN, ids)
                .toPreparedStatement(connection)
                .executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public QueryResult search(
            String quizName,
            boolean published,
            QuizType type,
            int page, int pageSize
    ) {
        List<QuizInformation> ret = new ArrayList<>();
        int count = 0;

        try {
            QueryBuilder query = new QueryBuilder(LISTING_QUERY)
                    .where("IsPublished", Operator.EQUALS, published)
                    .orderBy("q.UpdatedTime", OrderDirection.DESC)
                    .orderBy("q.SubjectId", OrderDirection.ASC);

            if (quizName != null && !quizName.isBlank()) {
                query.where("QuizName", Operator.LIKE, "%" + quizName.trim() + "%");
            }

            if (type != null) {
                query.where("QuizType", Operator.EQUALS, type.toInt());
            }

            ResultSet rs = new QueryBuilder(COUNT_LISTING_QUERY, query)
                    .toPreparedStatement(connection)
                    .executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs = query
                    .page(page, pageSize)
                    .toPreparedStatement(connection)
                    .executeQuery();

            while (rs.next()) {
                ret.add(new QuizInformation(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new QueryResult(count, pageSize, ret);
    }
}
