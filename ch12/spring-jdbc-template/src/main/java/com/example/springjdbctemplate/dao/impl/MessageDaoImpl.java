package com.example.springjdbctemplate.dao.impl;


import com.example.springjdbctemplate.dao.MessageDao;
import com.example.springjdbctemplate.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageDaoImpl implements MessageDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_MESSAGE_LIST = "select username, millis, blabla from T_MESSAGE where USERNAME = ? order by millis desc ";

    @Override
    public List<Message> getMessageByUsername(String paramUsername) {
        return jdbcTemplate.query(SELECT_MESSAGE_LIST, resultSet -> {
            List<Message> messageList = new ArrayList<>();
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                long millis = resultSet.getLong("millis");
                String blabla = resultSet.getString("blabla");
                messageList.add(new Message(username, millis, blabla));
            }
            return messageList;
        }, paramUsername);
    }
}
