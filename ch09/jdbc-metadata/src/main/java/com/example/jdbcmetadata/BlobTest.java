package com.example.jdbcmetadata;

import java.io.InputStream;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        String query = "select * from image";
        Path outPutPath = Paths.get("D:\\common\\temp\\files");

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
             PreparedStatement insertPreparedStatement = connection.prepareStatement(insert);
             PreparedStatement queryPreparedStatement = connection.prepareStatement(query)) {
            insertPreparedStatement.setString(1, fileName);
            insertPreparedStatement.setBlob(2, Files.newInputStream(path));
            int updateCount = insertPreparedStatement.executeUpdate();
            System.out.printf("updateCount = %d%n", updateCount);
            // updateCount = 1
            ResultSet resultSet = queryPreparedStatement.executeQuery();
            while (resultSet.next()) {
                String fileNameItem = resultSet.getString("file_name");
                InputStream bytes = resultSet.getBinaryStream("bytes");
                Files.copy(bytes, outPutPath.resolve(fileNameItem), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
