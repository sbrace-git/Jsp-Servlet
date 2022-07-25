package com.example.jdbcmetadata;

import java.sql.*;

public class TFilesInfo {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "jdbc:h2:D:\\server\\H2\\data\\test";
        String user = "sa";
        String password = "";
        String querySql = "select * from t_message";
        String insert = "insert into t_message(name,email,msg) values ('justin','justin@mail.com','message...')";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            if (statement.execute(insert)) {
                System.out.println("execute return true");
            } else {
                int updateCount = statement.getUpdateCount();
                System.out.printf("updateCount = %d\n", updateCount);
            }

            if (statement.execute(querySql)) {
                try (ResultSet resultSet = statement.getResultSet()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        String msg = resultSet.getString("msg");
                        System.out.printf("id = %d\n", id);
                        System.out.printf("name = %s\n", name);
                        System.out.printf("email = %s\n", email);
                        System.out.printf("msg = %s\n", msg);
                    }
                }
            } else {
                int updateCount = statement.getUpdateCount();
                System.out.printf("updateCount = %d\n", updateCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
