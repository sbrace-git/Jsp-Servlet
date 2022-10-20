package com.example.springdi.dao;


import com.example.springdi.model.Message;

import java.util.List;

public interface MessageDao {

    List<Message> getMessageByUsername(String username);

    void createMessage(Message message);

    void deleteMessage(String username, Long millis);

    List<Message> newMessageList();

}
