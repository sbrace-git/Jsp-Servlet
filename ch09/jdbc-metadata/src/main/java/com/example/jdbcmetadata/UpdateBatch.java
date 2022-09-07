package com.example.jdbcmetadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Collectors;

public class UpdateBatch {
    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String url = "jdbc:h2:tcp://localhost:9092/D:/server/H2/data/test";
//        String user = "sa";
//        String password = "";
        String url = "jdbc:mysql://localhost:3307/ch_09?rewriteBatchedStatements=true";
        String user = "root";
        String password = "1111";
        String insert = "insert into t_message(name,email,msg) values ('justin','justin@mail.com','message...')";
        String prepareInsert = "insert into t_message(name,email,msg) values (?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             PreparedStatement preparedStatement = connection.prepareStatement(prepareInsert)) {
            for (int i = 0; i < 100; i++) {
                statement.addBatch(insert);
                preparedStatement.setString(1, "prepare_name");
                preparedStatement.setString(2, "prepare_email");
                preparedStatement.setString(3, "prepare_msg");
                preparedStatement.addBatch();
            }
            int[] ints = statement.executeBatch();
            System.out.printf("ints = %s%n", Arrays.stream(ints).mapToObj(String::valueOf)
                    .collect(Collectors.joining(",", "[", "]")));
            // ints = [1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1]

            int[] prepareInts = preparedStatement.executeBatch();
            System.out.printf("prepareInt = %s%n", Arrays.stream(prepareInts).mapToObj(String::valueOf)
                    .collect(Collectors.joining(",", "[", "]")));
            // prepareInt = [-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2]
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
