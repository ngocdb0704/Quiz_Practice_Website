package app.dal;

import app.entity.Answer;
import app.entity.Attempt;
import app.entity.AttemptQuestion;
import app.entity.Question;
import app.entity.QuizInformation;
import java.sql.Timestamp;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DAOAttempt extends DBContext {
    public Attempt createAttempt(int quizId, int userId) {
        DAOQuiz quiz = new DAOQuiz();

        try {
            QuizInformation quizInfo = quiz.getQuizById(quizId);

            if (quizInfo == null) {
                throw new Exception("Could not create attempt for an unknown quiz");
            }

            quiz.close();

            QueryBuilder builder = new QueryBuilder();
            builder.setReturnKeys(true);
            builder.insertInto("[Attempt]", "QuizId", "UserId", "DueDate");

            int duration = quizInfo.getDurationInMinutes();
            Instant ins = Instant.now().plus(duration,  ChronoUnit.MINUTES);
            
            builder.values(quizId, userId, Timestamp.from(ins));

            PreparedStatement stmt = builder.toPreparedStatement(connection);
            
            int n = stmt.executeUpdate();

            if (n != 1) {
                throw new Exception("Could not create attempt for quiz " + quizId + ", no rows were affected");
            }

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) {
                throw new Exception("Could not get generated id for the created quiz");
            }

            int id = rs.getInt(1);

            Attempt attempt = getAttemptById(id);

            if (attempt == null) return null;

            prepareAnswerSheet(attempt);

            return attempt;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private void prepareAnswerSheet(Attempt attempt) throws Exception {
        ResultSet rs = new QueryBuilder("select QuestionId from [QuizQuestion]")
                            .whereAnd("QuizId", QueryBuilder.Operator.EQUALS, attempt.getQuizId())
                            .toPreparedStatement(connection)
                            .executeQuery();

        QueryBuilder insert = new QueryBuilder();
        insert.insertInto("[AttemptQuestionAnswer]", "AttemptId", "QuestionId");

        while (rs.next()) {
            insert.values(attempt.getAttemptId(), rs.getInt(1));
        }

        insert.toPreparedStatement(connection).executeUpdate();
    }

    private boolean saveAttempt(Attempt attempt) {
        try {
            PreparedStatement stmt = connection.prepareStatement("update [Attempt] set [CorrectCount] = ?, [DueDate] = ? where [AttemptId] = ?");
            stmt.setInt(1, attempt.getCorrectCount());
            stmt.setTimestamp(2, attempt.getDueDate());
            stmt.setInt(3, attempt.getAttemptId());

            int n = stmt.executeUpdate();

            if (n != 1) return false;

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Attempt getAttemptById(int attemptId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("select * from [Attempt] where [AttemptId] = ?");
            stmt.setInt(1, attemptId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Attempt(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public int getScore(int attemptId) {
        String sql = """
                     select count(*) from [AttemptQuestionAnswer] aqa
                     inner join [Answer] a on aqa.AnswerId = a.AnswerID
                     where AttemptId = ? and IsCorrect = 1""";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, attemptId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } 
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public boolean finishAttempt(int attemptId) {
        try {
            Attempt attempt = getAttemptById(attemptId);
            if (attempt == null) {
                throw new Exception("Could not find attempt id " + attemptId);
            }

            attempt.setDueDate(Timestamp.from(Instant.now()));

            return saveAttempt(attempt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean answerQuestion(int attemptId, int questionId, int answerId) {
        try {
            Attempt attempt = getAttemptById(attemptId);

            if (attempt == null) {
                throw new Exception("Cannot answer on non-existent attempt " + attemptId);
            }

            if (attempt.isFinished()) {
                throw new Exception("Attempt is past its due-date, cannot answer this attempt id " + attemptId);
            }

            PreparedStatement stmt = connection.prepareStatement("update [AttemptQuestionAnswer] set [AnswerId] = ? where [AttemptId] = ? and [QuestionId] = ?");

            stmt.setInt(1, answerId);
            stmt.setInt(2, attemptId);
            stmt.setInt(3, questionId);

            int n = stmt.executeUpdate();

            if(n != 1) {
                throw new Exception("Could not answer question, update count return not 1");
            }

            int score = getScore(attemptId);

            if (score == -1) {
                throw new Exception("Could not get score, returns -1");
            }

            attempt.setCorrectCount(score);
            saveAttempt(attempt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<AttemptQuestion> getAllAttemptsWithoutQuestion(int attemptId) {
        List<AttemptQuestion> questions = new ArrayList<>();

        try {
            QueryBuilder query = new QueryBuilder("select * from [AttemptQuestionAnswer]")
                .whereAnd("AttemptId", QueryBuilder.Operator.EQUALS, attemptId)
                .orderBy("QuestionId", QueryBuilder.OrderDirection.ASC);

            ResultSet rs = query.toPreparedStatement(connection).executeQuery();

            while (rs.next()) {
                questions.add(new AttemptQuestion(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return questions;
    }

    public QueryResult paginateAttemptQuestions(int attemptId, int pageNo) {
        List<AttemptQuestion> ret = new ArrayList<>();
        QuestionDAO qdao = new QuestionDAO();
        int count = 0;

        try {
            QueryBuilder query = new QueryBuilder("select * from [AttemptQuestionAnswer]");
            ResultSet rs = query
                .whereAnd("AttemptId", QueryBuilder.Operator.EQUALS, attemptId)
                .orderBy("QuestionId", QueryBuilder.OrderDirection.ASC)
                .page(pageNo, 1)
                .toPreparedStatement(connection)
                .executeQuery();

            while (rs.next()) {
                AttemptQuestion attemptQuestion = new AttemptQuestion(rs);

                Question question = qdao.getQuestion(attemptQuestion.getQuestion().getQuestionID());
                attemptQuestion.setQuestion(question);

                ret.add(attemptQuestion);
            }

            QueryBuilder countQuery = new QueryBuilder("select count(*) from [AttemptQuestionAnswer]", query);
            rs = countQuery.toPreparedStatement(connection).executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            qdao.close();
        }

        return new QueryResult(count, 1, ret);
    }

    public boolean toggleMarkQuestion(int attemptId, int questionId) {
        try {
            Attempt attempt = getAttemptById(attemptId);

            if (attempt == null) {
                throw new Exception("Cannot mark on non-existent attempt " + attemptId);
            }

            if (attempt.isFinished()) {
                throw new Exception("Attempt is past its due-date, cannot mark this attempt id " + attemptId);
            }

            PreparedStatement stmt = connection.prepareStatement("update [AttemptQuestionAnswer] set [Marked] = [Marked] ^ 1 where [AttemptId] = ? and [QuestionId] = ?");

            stmt.setInt(1, attemptId);
            stmt.setInt(2, questionId);

            int n = stmt.executeUpdate();

            return n == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        DAOAttempt da = new DAOAttempt();
        Attempt attempt = da.createAttempt(13, 1);
        da.answerQuestion(attempt.getAttemptId(), 1, 2);
        da.toggleMarkQuestion(attempt.getAttemptId(), 1);

        QueryResult<AttemptQuestion> result = da.paginateAttemptQuestions(attempt.getAttemptId(), 1);
        System.out.println(result.getTotalPages());
        AttemptQuestion q = result.getResults().get(0);

        System.out.println(q.getQuestion().getQuestionName());
        for (Answer ans : q.getQuestion().getAnswers()) {
            System.out.print(ans.getAnswerName());
            System.out.println(ans.getAnswerID() == q.getSelectedAnswer() ? " (selected)" : "");
        }
    }
}
