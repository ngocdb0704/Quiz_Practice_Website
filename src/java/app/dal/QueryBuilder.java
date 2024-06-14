package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private String baseSql;
    private int existingParamCount;
    private List<Object> parameters;
    private StringBuilder whereClause;
    private StringBuilder orderByClause;

    private int pageNumber = -1; 
    private int pageSize = -1;   
    private boolean isLoggingEnabled = false;

    public static enum OrderDirection {
        ASC, DESC
    }

    public static enum Operator {
        IN("IN"),
        BETWEEN("BETWEEN"),
        EQUALS("="),
        LIKE("LIKE");

        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public QueryBuilder(String baseSql) {
        this.baseSql = baseSql.trim();
        this.existingParamCount = countQuestionMarks(baseSql);
        this.whereClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.parameters = new ArrayList<>();
    }

    public QueryBuilder(String baseSql, QueryBuilder existing) {
        this.baseSql = baseSql.trim();
        this.existingParamCount = countQuestionMarks(baseSql);

        this.whereClause = new StringBuilder(existing.whereClause);
        this.orderByClause = new StringBuilder();
        this.parameters = new ArrayList<>();
        this.parameters.addAll(existing.parameters);
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

    public QueryBuilder where(String column, Operator operator, Object... values) throws IllegalArgumentException {
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }

        whereClause.append(column).append(" ").append(operator.getName());

        switch (operator) {
            case LIKE, EQUALS:
                whereClause.append(" ?");
                parameters.add(values[0]);
                break;
            case BETWEEN:
                if (values.length != 2) {
                    throw new IllegalArgumentException("BETWEEN operator requires exactly two values");
                }   whereClause.append(" ? AND ?");
                parameters.add(values[0]);
                parameters.add(values[1]);
                break;
            case IN:
                whereClause.append(" (");
                for (int i = 0; i < values.length; i++) {
                    whereClause.append("?");
                    if (i < values.length - 1) {
                        whereClause.append(", ");
                    }
                    parameters.add(values[i]);
                }   whereClause.append(")");
                break;
        }
        return this;
    }

    public QueryBuilder orderBy(String column, OrderDirection direction) {
        if (!orderByClause.isEmpty()) {
            orderByClause.append(",");
        }
        orderByClause.append(String.format(" %s %s", column, direction.toString()));
        return this;
    }

    public QueryBuilder page(int pageNumber, int pageSize) throws IllegalStateException {
        if (orderByClause.isEmpty()) {
            throw new IllegalStateException("page cannot be called before orderBy");
        }

        this.pageNumber = pageNumber > 0 ? pageNumber : 0;
        this.pageSize = pageSize > 1 ? pageSize : 1;
        return this;
    }

    public QueryBuilder setLoggingEnabled(boolean enabled) {
        this.isLoggingEnabled = enabled;
        return this;
    }

    public PreparedStatement toPreparedStatement(Connection connection) throws SQLException {
        String finalSql = baseSql;

        if (whereClause.length() > 0) {
            finalSql += " WHERE " + whereClause;
        }

        if (!orderByClause.isEmpty()) {
            finalSql += " ORDER BY " + orderByClause;
        }

        if (pageNumber > 0 && pageSize > 0) {
            finalSql += " OFFSET ? ROWS " +
                        "FETCH NEXT ? ROWS ONLY";
        } 

        if (isLoggingEnabled) {
            System.out.println(finalSql);
        }

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
