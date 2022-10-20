package com.example.springdi.template;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetExtractor<T> {
    T extractor(ResultSet resultSet) throws SQLException;
}
