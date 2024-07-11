package app.dal;

import static app.dal.QueryBuilder.Operator;
import static app.dal.QueryBuilder.OrderDirection;
import app.entity.BlogInformation;
import app.entity.Question;
import app.entity.QuizInformation;
import app.entity.QuizLesson;
import app.entity.QuizType;
import app.entity.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;

public class DAOQuiz extends DBContext {

    private static final String LISTING_QUERY
            = """
            select q.*, s.SubjectTitle from [Quiz] q
            inner join [Subject] s on q.SubjectId = s.SubjectId""";

    private static final String COUNT_LISTING_QUERY
            = "select count(*) from [Quiz] q\n"
            + "inner join [Subject] s on q.SubjectId = s.SubjectId";

    private static final String SIMULATION_QUERY
            = """
             with
             activeSubjectsCte as (
                select SubjectId, Email from [Registration] r
                inner join [Package] p on r.PackageId = p.PackageId
                inner join [User] u on u.UserId = r.UserId
                where r.RegistrationStatusId = 3
             )
             select q.*, s.SubjectTitle, activeSubjectsCte.Email from [Quiz] q
             inner join [Subject] s on q.SubjectId = s.SubjectId
             inner join activeSubjectsCte on activeSubjectsCte.SubjectId = q.SubjectId
             """;

    private static final String COUNT_SIMULATION_QUERY
            = """
            with
            activeSubjectsCte as (
                select SubjectId, Email from [Registration] r
                inner join [Package] p on r.PackageId = p.PackageId
                inner join [User] u on u.UserId = r.UserId
                where r.RegistrationStatusId = 3
            )
            select count(*) from [Quiz] q
            inner join [Subject] s on q.SubjectId = s.SubjectId
            inner join activeSubjectsCte on activeSubjectsCte.SubjectId = q.SubjectId
            """;

    public int delete(int id) {
        try {
            return new QueryBuilder("delete from [Quiz]")
                    .whereAnd("QuizId", Operator.EQUALS, id)
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
        if (id == null || n <= 0) {
            return;
        }

        try {
            QuizInformation quiz = getQuizById(id);

            if (quiz == null) {
                return;
            }

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
            int subjectId,
            QuizType type,
            int page, int pageSize
    ) {
        List<QuizInformation> ret = new ArrayList<>();
        int count = 0;

        try {
            QueryBuilder query = new QueryBuilder(LISTING_QUERY)
                    .setLoggingEnabled(true)
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

    public List<Subject> getSubjectsWithSimulationExams(String email) {
        List<Subject> ret = new ArrayList<>();

        try {
            ResultSet rs = new QueryBuilder("""
                with
                activeSubjectsCte as (
                	select SubjectId, Email from [Registration] r
                	inner join [Package] p on r.PackageId = p.PackageId
                	inner join [User] u on u.UserId = r.UserId
                	where r.RegistrationStatusId = 3
                )
                select distinct q.SubjectId, SubjectTitle from [Quiz] q
                inner join [Subject] s on q.SubjectId = s.SubjectId
                inner join activeSubjectsCte on activeSubjectsCte.SubjectId = q.SubjectId
            """
            )
                    .whereAnd("Email", Operator.EQUALS, email)
                    .toPreparedStatement(connection).executeQuery();

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

    public QueryResult getSimulationExams(
            int subjectId,
            String examName,
            String email,
            int page, int pageSize
    ) {
        List<QuizInformation> ret = new ArrayList<>();
        int count = 0;

        try {

            QueryBuilder query = new QueryBuilder(SIMULATION_QUERY);

            if (subjectId != -1) {
                query.whereAnd("q.SubjectId", Operator.EQUALS, subjectId);
            }

            if (examName != null && !examName.isBlank()) {
                query.whereAnd("QuizName", Operator.LIKE, "%" + examName.trim() + "%");
            }

            query.whereAnd("QuizType", Operator.EQUALS, QuizType.SIMULATION.toInt());
            query.whereAnd("Email", Operator.EQUALS, email);

            ResultSet rs = query
                    .orderBy("SubjectTitle", OrderDirection.ASC)
                    .page(page, pageSize)
                    .toPreparedStatement(connection)
                    .executeQuery();

            while (rs.next()) {
                ret.add(new QuizInformation(rs));
            }

            rs = new QueryBuilder(COUNT_SIMULATION_QUERY, query)
                    .toPreparedStatement(connection)
                    .executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
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

    public List<QuizLesson> getGroupQuestionByLesson(int quizId) {
        List<QuizLesson> list = new ArrayList<>();
        String sql = "select *\n"
                + "from QuizLessonQuestionCount\n"
                + "where QuizId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizLesson(rs.getInt("QuizId"),
                        rs.getInt("LessonId"),
                        rs.getInt("QuestionCount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int addQuiz(QuizInformation quiz) {
        String sql = "INSERT INTO [dbo].[Quiz] "
                + "([SubjectId], [QuizName], [Level], [DurationInMinutes], [PassRate], [QuizType], [IsPublished], [UpdatedTime], [Description], [TotalQuestion]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, quiz.getSubjectId());
            stmt.setString(2, quiz.getQuizName());
            stmt.setInt(3, quiz.getLevel().toInt());
            stmt.setInt(4, quiz.getDurationInMinutes());
            stmt.setInt(5, quiz.getPassRate());
            stmt.setInt(6, quiz.getType().toInt());
            stmt.setInt(7, 1);
            stmt.setDate(8, Date.valueOf(LocalDate.now()));
            stmt.setString(9, quiz.getDescription());
            stmt.setInt(10, quiz.getTotalQuestion());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Assuming the first column is the generated QuizId
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Question> getAllQuestionsBySubject(int subjectId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM Question WHERE SubjectId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subjectId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQuestionID(rs.getInt("QuestionId"));
                // Set other properties of question
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public void addQuestionToQuiz(int quizId, int questionId) {
        String query = "INSERT INTO QuestionQuiz (QuizId, QuestionId) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            stmt.setInt(2, questionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuizLessonQuestionCount(int quizId, int lessonId, int questionCount) {
        String query = "INSERT INTO QuizLessonQuestionCount (QuizId, LessonId, QuestionCount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            stmt.setInt(2, lessonId);
            stmt.setInt(3, questionCount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestionsByLessonId(int lessonId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM Question WHERE LessonID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQuestionID(rs.getInt("QuestionID"));
                question.setQuestionName(rs.getString("QuestionText"));
                question.setExplanation(rs.getString("Explanation"));
                question.setLevel(rs.getInt("Level"));
                question.setSubjectID(rs.getInt("SubjectID"));
                question.setLessonID(rs.getInt("LessonID"));
                question.setStatus(rs.getInt("Status"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public boolean isQuizAttempted(int quizId) throws SQLException {
        String checkAttemptsSQL = "SELECT numberOfAttempts FROM Quiz WHERE QuizId = ?";
        try (PreparedStatement checkAttemptsStmt = connection.prepareStatement(checkAttemptsSQL)) {
            checkAttemptsStmt.setInt(1, quizId);
            ResultSet rs = checkAttemptsStmt.executeQuery();
            if (rs.next()) {
                int numberOfAttempts = rs.getInt("numberOfAttempts");
                return numberOfAttempts > 0;
            }
        }
        return false;
    }

    public boolean deleteQuiz(int quizId) throws SQLException {
        String deleteQuizSQL = "DELETE FROM Quiz WHERE QuizId = ?";
        String deleteQuestionsSQL = "DELETE FROM QuestionQuiz WHERE QuizId = ?";
        String deleteLessonQuestionsSQL = "DELETE FROM QuizLessonQuestionCount WHERE QuizId = ?";

        try (PreparedStatement deleteQuizStmt = connection.prepareStatement(deleteQuizSQL);
             PreparedStatement deleteQuestionsStmt = connection.prepareStatement(deleteQuestionsSQL);
             PreparedStatement deleteLessonQuestionsStmt = connection.prepareStatement(deleteLessonQuestionsSQL)) {

            // Begin transaction
            connection.setAutoCommit(false);

            // Delete from QuestionQuiz
            deleteQuestionsStmt.setInt(1, quizId);
            deleteQuestionsStmt.executeUpdate();

            // Delete from QuizLessonQuestionCount
            deleteLessonQuestionsStmt.setInt(1, quizId);
            deleteLessonQuestionsStmt.executeUpdate();

            // Delete from Quiz
            deleteQuizStmt.setInt(1, quizId);
            deleteQuizStmt.executeUpdate();

            // Commit transaction
            connection.commit();
            connection.setAutoCommit(true);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
            return false;
        }
    }

}
