package app.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private String baseSql;
    private int existingParamCount;
    private List<Object> parameters;
    private StringBuilder whereClause;
    private StringBuilder orderByClause;
    private StringBuilder groupByClause;

    private int pageNumber = -1;
    private int pageSize = -1;
    private boolean isLoggingEnabled = false;
    private boolean returnKeys = false;

    private boolean isInsert = false;
    private String[] insertColumns;
    private List<Object[]> insertValues;

    public static enum OrderDirection {
        ASC, DESC
    }

    public static enum Operator {
        IN("IN"),
        BETWEEN("BETWEEN"),
        EQUALS("="),
        GREATER(">"),
        SMALLER("<"),
        SMALLER_EQ("<="),
        GREATER_EQ(">="),
        LIKE("LIKE");

        private final String name;

        private Operator(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    
    public QueryBuilder() {
        this.existingParamCount = 0;
        this.whereClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.groupByClause = new StringBuilder();
        this.parameters = new ArrayList<>();
        this.insertValues = new ArrayList<>();
    }

    public QueryBuilder(String baseSql) {
        this.baseSql = baseSql.trim();
        this.existingParamCount = countQuestionMarks(baseSql);
        this.whereClause = new StringBuilder();
        this.orderByClause = new StringBuilder();
        this.groupByClause = new StringBuilder();
        this.parameters = new ArrayList<>();
        this.insertValues = new ArrayList<>();
    }

    public QueryBuilder(String baseSql, QueryBuilder existing) {
        this.baseSql = baseSql.trim();
        this.existingParamCount = countQuestionMarks(baseSql);

        this.whereClause = new StringBuilder(existing.whereClause);
        this.groupByClause = new StringBuilder(existing.groupByClause);
        this.orderByClause = new StringBuilder();
        this.parameters = new ArrayList<>();
        this.parameters.addAll(existing.parameters);
        this.insertValues = new ArrayList<>();
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

    public QueryBuilder whereAnd(String column, Operator operator, Object... values) throws IllegalArgumentException {
        if (whereClause.length() > 0) {
            whereClause.append(" AND ");
        }

        return where(column, operator, values);
    }

    public QueryBuilder whereOr(String column, Operator operator, Object... values) throws IllegalArgumentException {
        if (whereClause.length() > 0) {
            whereClause.append(" OR ");
        }

        return where(column, operator, values);
    }

    private QueryBuilder where(String column, Operator operator, Object... values) throws IllegalArgumentException {
        whereClause.append(column).append(" ").append(operator.getName());

        switch (operator) {
            case LIKE, EQUALS, GREATER, SMALLER, GREATER_EQ, SMALLER_EQ:
                whereClause.append(" ?");
                parameters.add(values[0]);
                break;
            case BETWEEN:
                if (values.length != 2) {
                    throw new IllegalArgumentException("BETWEEN operator requires exactly two values");
                }
                whereClause.append(" ? AND ?");
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
                }
                whereClause.append(")");
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

    public QueryBuilder randomize() {
        orderByClause.setLength(0);
        orderByClause.append("newid()");
        return this;
    }

    public QueryBuilder groupBy(String... columns) {
        for (String column : columns) {
            if (!groupByClause.isEmpty()) {
                groupByClause.append(",");
            }

            groupByClause.append(String.format(" %s", column));
        }

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

    public QueryBuilder setReturnKeys(boolean enabled) {
        this.returnKeys = enabled;
        return this;
    }

    public QueryBuilder insertInto(String table, String... columns) {
        this.baseSql = "INSERT INTO " + table;
        this.isInsert = true;
        this.insertColumns = columns;
        this.insertValues.clear();
        return this;
    }

    public QueryBuilder values(Object... values) {
        if (!isInsert) {
            throw new IllegalStateException("values can only be called after insertInto");
        }
        if (values.length != insertColumns.length) {
            throw new IllegalArgumentException("The number of values must match the number of columns");
        }

        insertValues.add(values);
        return this;
    }

    public PreparedStatement toPreparedStatement(Connection connection) throws SQLException {
        StringBuilder finalSql = new StringBuilder(baseSql);

        if (isInsert) {
            finalSql.append(" (").append(String.join(", ", insertColumns)).append(") VALUES ");
            for (int i = 0; i < insertValues.size(); i++) {
                if (i > 0) {
                    finalSql.append(", ");
                }
                finalSql.append("(");
                for (int j = 0; j < insertValues.get(i).length; j++) {
                    if (j > 0) {
                        finalSql.append(", ");
                    }
                    finalSql.append("?");
                }
                finalSql.append(")");
            }
        } else {
            if (whereClause.length() > 0) {
                finalSql.append(" WHERE ").append(whereClause);
            }

            if (groupByClause.length() > 0) {
                finalSql.append(" GROUP BY ").append(groupByClause);
            }

            if (!orderByClause.isEmpty()) {
                finalSql.append(" ORDER BY ").append(orderByClause);
            }

            if (pageNumber > 0 && pageSize > 0) {
                finalSql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
            }
        }

        if (isLoggingEnabled) {
            System.out.println(finalSql.toString());
        }

        PreparedStatement ps;

        if (returnKeys) {
            ps = connection.prepareStatement(finalSql.toString(), Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.prepareStatement(finalSql.toString());
        }

        int paramIndex = existingParamCount + 1;
        if (isInsert) {
            for (Object[] row : insertValues) {
                for (Object value : row) {
                    ps.setObject(paramIndex++, value);
                }
            }
        } else {
            for (Object param : parameters) {
                ps.setObject(paramIndex++, param);
            }

            if (pageNumber > 0 && pageSize > 0) {
                int offset = (pageNumber - 1) * pageSize;
                ps.setInt(paramIndex++, offset);
                ps.setInt(paramIndex, pageSize);
            }
        }

        return ps;
    }
}