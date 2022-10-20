package com.example.springjdbctemplate.service;

import com.example.springjdbctemplate.model.Message;

import java.util.List;

public interface UserService {

    List<Message> messages(String username);

    void addMessage(String username, String blabla);

    void deleteMessage(String username, String millis);
}
