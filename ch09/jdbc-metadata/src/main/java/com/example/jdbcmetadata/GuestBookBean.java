package com.example.jdbcmetadata;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public void addMessage(Message message) {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("insert into t_message(name,email,msg) values (" +
                    "'" + message.getName() +
                    "','" + message.getEmail() +
                    "','" + message.getMsg() +
                    "'" +
                    ")");
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
