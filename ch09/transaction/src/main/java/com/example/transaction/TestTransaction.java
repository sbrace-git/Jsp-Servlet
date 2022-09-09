package com.example.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTransaction {
    public static void main(String[] args) {

        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String url = "jdbc:h2:tcp://localhost:9092/D:/server/H2/data/test";
        String username = "sa";
        String password = "";

        String insert = "insert into t_message (name,email,msg) values ('name','email','msg....')";
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            int transactionIsolation = connection.getTransactionIsolation();

            switch (transactionIsolation) {
                case Connection.TRANSACTION_NONE:
                    System.out.println("TRANSACTION_NONE");
                    break;
                case Connection.TRANSACTION_READ_UNCOMMITTED:
                    System.out.println("TRANSACTION_READ_UNCOMMITTED");
                    break;
                case Connection.TRANSACTION_READ_COMMITTED:
                    System.out.println("TRANSACTION_READ_COMMITTED");
                    break;
                case Connection.TRANSACTION_REPEATABLE_READ:
                    System.out.println("TRANSACTION_REPEATABLE_READ");
                    break;
                case Connection.TRANSACTION_SERIALIZABLE:
                    System.out.println("TRANSACTION_SERIALIZABLE");
            }

            System.out.println("autoCommit = " + connection.getAutoCommit());
            connection.setAutoCommit(false);
            System.out.println("autoCommit = " + connection.getAutoCommit());

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(insert);
                statement.executeUpdate(insert);
                statement.executeUpdate(insert);
//                int test = 1 / 0;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
