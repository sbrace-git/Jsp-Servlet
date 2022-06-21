package com.example.jdbcmetadata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        try (Connection connection = DriverManager.getConnection(url, user, password)) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
