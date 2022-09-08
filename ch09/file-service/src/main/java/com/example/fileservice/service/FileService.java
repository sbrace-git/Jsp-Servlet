package com.example.fileservice.service;

import com.example.fileservice.entity.Image;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileService {
    private DataSource dataSource;

    public FileService() {
        System.out.println("FileService init");

        try {
            InitialContext initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/demo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final String select = "select file_name,bytes from image where id = ?";

    public Image getFile(Integer imageId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(select)) {
            preparedStatement.setInt(1, imageId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Image image = new Image();
                image.setId(imageId);
                image.setFileName(resultSet.getString("file_name"));
                image.setBytes(resultSet.getBytes("bytes"));
                return image;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Image> getFileList() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select id,file_name,create_time from image")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Image> images = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String fileName = resultSet.getString("file_name");
                Date createTime = resultSet.getDate("create_time");
                java.util.Date date = new java.util.Date(createTime.getTime());
                Image image = new Image();
                image.setId(id);
                image.setCreateTime(date);
                image.setFileName(fileName);
                images.add(image);
            }
            return images;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public void save(Image image) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into image (file_name,create_time,bytes) values (?,now(),?)")) {
            preparedStatement.setString(1, image.getFileName());
            preparedStatement.setBytes(2, image.getBytes());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from image where id = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
