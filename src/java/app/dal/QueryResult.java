package app.dal;

import java.util.List;

public class QueryResult<T> {

    private int totalItems;
    private int pageSize;
    private List<T> results;

    public QueryResult(int total, int pageSize, List<T> results) {
        this.totalItems = total;
        this.pageSize = pageSize;
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

    public int getTotalPages() {
        if (pageSize > 0) {
            return (int) (Math.ceil((double) totalItems / pageSize));
        }

        return 0;
    }
}