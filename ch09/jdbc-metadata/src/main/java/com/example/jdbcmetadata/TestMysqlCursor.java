package com.example.jdbcmetadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestMysqlCursor {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3307/ch_09?useCursorFetch=true";
        String user = "root";
        String password = "1111";
        String querySql = "select * from t_message";
        String insert = "insert into t_message(name,email,msg) values ('justin','justin@mail.com','message...')";
        String dml = "create table t_message\n" +
                "(\n" +
                "    id    int          not null auto_increment primary key,\n" +
                "    name  char(20)     not null,\n" +
                "    email char(40),\n" +
                "    msg   varchar(256) not null\n" +
                ");";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            System.out.printf("statementFetchSize = %d%n", statement.getFetchSize());
            statement.setFetchSize(10);
            System.out.printf("statementFetchSize = %d%n", statement.getFetchSize());

//            if (statement.execute(insert)) {
//                System.out.println("execute return true");
//            } else {
//                int updateCount = statement.getUpdateCount();
//                System.out.printf("updateCount = %d\n", updateCount);
//            }

            try (ResultSet resultSet = statement.executeQuery(querySql)) {

                System.out.printf("resultSetFetchSize = %d%n", resultSet.getFetchSize());
                resultSet.setFetchSize(10);
                System.out.printf("resultSetFetchSize = %d%n", resultSet.getFetchSize());

                /**
                 * query
                 */
                while (resultSet.next()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String msg = resultSet.getString("msg");
                    System.out.printf("id = %d\t", id);
                    System.out.printf("name = %s\t", name);
                    System.out.printf("email = %s\t", email);
                    System.out.printf("msg = %s\n", msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
