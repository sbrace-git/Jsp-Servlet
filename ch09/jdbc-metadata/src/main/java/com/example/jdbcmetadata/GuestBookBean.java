package com.example.jdbcmetadata;

import org.h2.util.StringUtils;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestBookBean implements Serializable {

    public GuestBookBean() {
        try {
            Class.forName("org.h2.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String url = "jdbc:h2:D:\\server\\H2\\data\\test";
    private String user = "sa";
    private String password = "";
    private static final String insertSql =
            "insert into t_message(name,email,msg) values (?,?,?)";
    private static final String querySql = "select * from t_message";

    public void addMessage(Message message) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, message.getName());
            preparedStatement.setString(2, message.getEmail());
            preparedStatement.setString(3, message.getMsg());
            System.out.println(preparedStatement);
            int update = preparedStatement.executeUpdate();
            System.out.println("update = " + update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<Message> messageList(String searchName) {
        String sql = querySql;
        if (!StringUtils.isNullOrEmpty(searchName)) {
            sql = querySql + " where name = ?";
        }
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            if (!StringUtils.isNullOrEmpty(searchName)) {
                preparedStatement.setString(1,searchName);
            }
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Message> messages = new ArrayList<>();
            while (resultSet.next()) {
                Message message = new Message();
                message.setId(resultSet.getLong(1));
                message.setName(resultSet.getString(2));
                message.setEmail(resultSet.getString(3));
                message.setMsg(resultSet.getString(4));
                messages.add(message);
            }
            return messages;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
