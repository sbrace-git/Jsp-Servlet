package com.example.springjdbctemplate.service.impl;

import com.example.springjdbctemplate.dao.MessageDao;
import com.example.springjdbctemplate.model.Message;
import com.example.springjdbctemplate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private MessageDao messageDao;

    @Override
    public List<Message> messages(String username) {
        return messageDao.getMessageByUsername(username);
    }

}
