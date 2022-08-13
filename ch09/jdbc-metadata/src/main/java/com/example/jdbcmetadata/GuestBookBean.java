package com.example.jdbcmetadata;

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

    public void addMessage(Message message) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
            preparedStatement.setString(1, message.getName());
            preparedStatement.setString(2, message.getEmail());
            preparedStatement.setString(3, message.getMsg());
            int update = preparedStatement.executeUpdate();
            System.out.println("update = " + update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages() {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery("select * from t_message");
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
