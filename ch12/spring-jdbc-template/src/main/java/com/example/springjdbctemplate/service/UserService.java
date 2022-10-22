package com.example.springjdbctemplate.service;

import com.example.springjdbctemplate.model.Message;

import java.util.List;

public interface UserService {

    List<Message> messages(String username);
}
