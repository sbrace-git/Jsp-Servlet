package com.example.jdbcmetadata;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
import java.sql.SQLException;

public class MessagePredicate implements Predicate {

    private String columnName;
    private String columnValue;
    private Integer columnIndex;
    private FilteredRowSet filteredRowSet;

    public MessagePredicate(FilteredRowSet filteredRowSet, String columnValue, String columnName) {
        this.filteredRowSet = filteredRowSet;
        this.columnName = columnName;
        this.columnValue = columnValue;
    }

    public MessagePredicate(FilteredRowSet filteredRowSet, String columnValue, int columnIndex) {
        this.filteredRowSet = filteredRowSet;
        this.columnIndex = columnIndex;
        this.columnValue = columnValue;
    }

    @Override
    public boolean evaluate(RowSet rs) {
        try {
            if (null == columnIndex) {
                return evaluate(columnValue, columnName);
            } else {
                return evaluate(columnValue, columnIndex);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean evaluate(Object value, int column) throws SQLException {
        return value.equals(filteredRowSet.getString(column));
    }

    @Override
    public boolean evaluate(Object value, String columnName) throws SQLException {
        return value.equals(filteredRowSet.getString(columnName));
    }

}
