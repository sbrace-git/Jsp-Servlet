package com.example.springjdbctemplate;

import com.example.springjdbctemplate.model.Message;
import com.example.springjdbctemplate.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        final String BLABLA_FORMAT = "%s\t%s%n";
        List<Message> messageList = userService.messages("test");
        messageList.forEach(message ->
                System.out.printf(BLABLA_FORMAT, message.getLocalDateTime(), message.getBlabla()));
    }
}
