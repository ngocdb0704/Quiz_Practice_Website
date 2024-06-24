package app.dal;

import static app.dal.QueryBuilder.Operator;
import static app.dal.QueryBuilder.OrderDirection;
import app.entity.BlogInformation;
import app.entity.QuizInformation;
import app.entity.QuizType;
import app.entity.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DAOQuiz extends DBContext {
    private static final String LISTING_QUERY =
            """
            with cte as (
              select q.QuizId, count(QuestionId) as QuestionCount from [Quiz] q
              left join [QuizQuestion] qq on q.QuizId = qq.QuizId
              group by q.QuizId
            )
            select q.*, s.SubjectTitle, QuestionCount from cte
            inner join [Quiz] q on q.QuizId = cte.QuizId
            inner join [Subject] s on q.SubjectId = s.SubjectId""";

    private static final String COUNT_LISTING_QUERY =
            "select count(*) from [Quiz] q\n"
            + "inner join [Subject] s on q.SubjectId = s.SubjectId";

    public int markDraft(Integer[] ids) {
        try {
            return new QueryBuilder("update [Quiz] set IsPublished = 0, "
                    + "UpdatedTime = CURRENT_TIMESTAMP")
                .whereAnd("QuizId", Operator.IN, ids)
                .toPreparedStatement(connection)
                .executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    public int publish(Integer[] ids) {
        try {
            return new QueryBuilder("update [Quiz] set IsPublished = 1, "
                    + "UpdatedTime = CURRENT_TIMESTAMP")
                .whereAnd("QuizId", Operator.IN, ids)
                .toPreparedStatement(connection)
                .executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }

    public int delete(Integer[] ids) {
        try {
            return new QueryBuilder("delete from [Quiz]")
                .whereAnd("QuizId", Operator.IN, ids)
                .toPreparedStatement(connection)
                .executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return 0;
    }
    
    public List<Subject> getSubjectsWithQuiz() {
        List<Subject> ret = new ArrayList<>();
        
        try {
            ResultSet rs = new QueryBuilder("""
                select distinct s.SubjectId, SubjectTitle from [Subject] s
                inner join Quiz q on s.SubjectId = q.SubjectId
            """
            ).toPreparedStatement(connection).executeQuery();
            
            while (rs.next()) {
                Subject s = new Subject();
                s.setSubjectId(rs.getInt("SubjectId"));
                s.setSubjectName(rs.getString("SubjectTitle"));
                ret.add(s);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return ret;
    }

    public QuizInformation getQuizById(Integer id) {
        try {
            ResultSet rs = new QueryBuilder(LISTING_QUERY)
                            .whereAnd("q.QuizId", Operator.EQUALS, id)
                            .toPreparedStatement(connection)
                            .executeQuery();

            if (rs.next()) {
                return new QuizInformation(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void randomizeForQuiz(Integer id, int n) {
        if (id == null || n <= 0) return;

        try {
            QuizInformation quiz = getQuizById(id);

            if (quiz == null) return;
            
            PreparedStatement stmt = new QueryBuilder("select top (?) QuestionID from [Question]")
                .whereAnd("SubjectId", Operator.EQUALS, quiz.getSubjectId())
                .whereAnd("Status", Operator.EQUALS, 1)
                .randomize()
                .toPreparedStatement(connection);
            stmt.setInt(1, n);

            ResultSet rs = stmt.executeQuery();
            List<Integer> questionIds = new ArrayList<>();
            while (rs.next()) {
                questionIds.add(rs.getInt("QuestionID"));
            }

            if (questionIds.isEmpty()) {
                return;
            }

            new QueryBuilder("delete from [QuizQuestion]")
                    .whereAnd("QuizId", Operator.EQUALS, id)
                    .toPreparedStatement(connection)
                    .executeUpdate();
            
            QueryBuilder insertQuery = new QueryBuilder();
            insertQuery.insertInto("[QuizQuestion]", "QuizId", "QuestionId");
            
            for (Integer questionId : questionIds) {
                insertQuery.values(id, questionId);
            }
            
            insertQuery.toPreparedStatement(connection).executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public QueryResult search(
            String quizName,
            boolean published,
            int subjectId,
            QuizType type,
            int page, int pageSize
    ) {
        List<QuizInformation> ret = new ArrayList<>();
        int count = 0;

        try {
            QueryBuilder query = new QueryBuilder(LISTING_QUERY)
                    .setLoggingEnabled(true)
                    .whereAnd("IsPublished", Operator.EQUALS, published)
                    .orderBy("QuestionCount", OrderDirection.DESC)
                    .orderBy("q.UpdatedTime", OrderDirection.DESC)
                    .orderBy("q.SubjectId", OrderDirection.ASC);

            if (quizName != null && !quizName.isBlank()) {
                query.whereAnd("QuizName", Operator.LIKE, "%" + quizName.trim() + "%");
            }

            if (type != null) {
                query.whereAnd("QuizType", Operator.EQUALS, type.toInt());
            }
            
            if (subjectId != -1) {
                query.whereAnd("q.SubjectId", Operator.EQUALS, subjectId);
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
    
    public static void main(String[] args) throws SQLException {
        DAOQuiz qq = new DAOQuiz();
        
        ResultSet rs = new QueryBuilder("select QuizId from [Quiz]")
                .toPreparedStatement(qq.connection)
                .executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt(1);
            qq.randomizeForQuiz(id, 50);
        }
    }
}
