package com.example.springjdbctemplate.dao;


import com.example.springjdbctemplate.model.Message;

import java.util.List;

public interface MessageDao {

    List<Message> getMessageByUsername(String username);

    void createMessage(Message message);

    void deleteMessage(String username, Long millis);

}
