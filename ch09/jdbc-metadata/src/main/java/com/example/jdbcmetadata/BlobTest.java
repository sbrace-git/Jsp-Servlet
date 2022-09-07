package com.example.jdbcmetadata;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BlobTest {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3307/ch_09?rewriteBatchedStatements=true";
        String user = "root";
        String password = "1111";
        String insert = "insert into image (file_name, create_time, bytes) values(?, now(), ?)";
        Path path = Paths.get("D:\\common\\temp\\text.txt");
        String fileName = path.getFileName().toString();

        /**
         * CREATE TABLE `image` (
         *   `id` int NOT NULL AUTO_INCREMENT,
         *   `file_name` varchar(255) NOT NULL,
         *   `create_time` datetime NOT NULL,
         *   `bytes` blob NOT NULL,
         *   PRIMARY KEY (`id`)
         * )
         */
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
            preparedStatement.setString(1, fileName);
            preparedStatement.setBlob(2, Files.newInputStream(path));
            int updateCount = preparedStatement.executeUpdate();
            System.out.printf("updateCount = %d%n", updateCount);
            // updateCount = 1
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
