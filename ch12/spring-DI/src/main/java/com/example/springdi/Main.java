package com.example.springdi;

import com.example.springdi.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        final String BLABLA_FORMAT = "%s\t%s%n";
        userService.messages("test").forEach(message ->
                System.out.printf(BLABLA_FORMAT, message.getLocalDateTime(), message.getBlabla()));

    }
}
