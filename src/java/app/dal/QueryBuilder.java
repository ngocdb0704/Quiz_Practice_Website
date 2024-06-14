package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private String baseSql;
    private int existingParamCount;
    private List<Object> parameters = new ArrayList<>();
    private StringBuilder whereClause = new StringBuilder();

    private int pageNumber = -1; 
    private int pageSize = -1;   
    private boolean orderByCalled = false;
    private boolean whereCalled = false;

    public enum OrderDirection {
        ASC, DESC
    }

    public enum Operator {
        IN("IN"), BETWEEN("BETWEEN"), EQUALS("=");
        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public QueryBuilder(String baseSql) {
        this.baseSql = baseSql;
        this.existingParamCount = countQuestionMarks(baseSql);
    }

    private int countQuestionMarks(String sql) {
        int count = 0;
        for (char c : sql.toCharArray()) {
            if (c == '?') {
                count++;
            }
        }
        return count;
    }

    public QueryBuilder where(String column, Operator operator, Object... values) throws Exception {
        if (orderByCalled) {
            throw new Exception("where must be called before orderBy");
        }

        whereCalled = true;
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }

        whereClause.append(column).append(" ").append(operator.getName());

        if (operator == Operator.EQUALS) {
            whereClause.append(" ?");
            parameters.add(values[0]);
        } else if (operator == Operator.BETWEEN) {
            if (values.length != 2) {
                throw new Exception("BETWEEN operator requires exactly two values");
            }
            whereClause.append(" ? AND ?");
            parameters.add(values[0]);
            parameters.add(values[1]);
        } else {  // IN operator
            whereClause.append(" (");
            for (int i = 0; i < values.length; i++) {
                whereClause.append("?");
                if (i < values.length - 1) {
                    whereClause.append(", ");
                }
                parameters.add(values[i]);
            }
            whereClause.append(")");
        }
        return this;
    }

    public QueryBuilder orderBy(String column, OrderDirection direction) throws Exception {
        if (!whereCalled) {
            throw new Exception("orderBy cannot be called before where");
        }

        orderByCalled = true;
        baseSql += " ORDER BY " + column + " " + direction.name();
        return this;
    }

    public QueryBuilder page(int pageNumber, int pageSize) throws Exception {
        if (!orderByCalled) {
            throw new Exception("page cannot be called before orderBy");
        }

        if (pageNumber < 1 || pageSize < 1) {
            throw new Exception("Page number and page size must be positive");
        }
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        return this;
    }

    public PreparedStatement toPreparedStatement(Connection connection) throws SQLException {
        String finalSql = baseSql;

        if (whereClause.length() > 0) {
            finalSql += " WHERE " + whereClause;
        }

        if (pageNumber > 0 && pageSize > 0) {
            finalSql += " OFFSET ? ROWS " +
                        "FETCH NEXT ? ROWS ONLY";
        } 

        System.out.println(finalSql);

        PreparedStatement ps = connection.prepareStatement(finalSql);
        int paramIndex = existingParamCount + 1;
        for (Object param : parameters) {
            ps.setObject(paramIndex++, param);
        }

        if (pageNumber > 0 && pageSize > 0) {
            int offset = (pageNumber - 1) * pageSize;
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, pageSize);
        }

        return ps;
    }
}
