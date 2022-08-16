package com.example.jdbcmetadata;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.Connection;

public class DatabaseBean implements Serializable {
    private DataSource dataSource;

    public DatabaseBean() {
        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/demo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConnectedOk() {
        try (Connection connection = dataSource.getConnection()) {
            return !connection.isClosed();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
