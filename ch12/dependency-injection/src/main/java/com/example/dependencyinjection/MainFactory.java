package com.example.dependencyinjection;

import com.example.dependencyinjection.factory.Service;
import com.example.dependencyinjection.service.UserService;

public class MainFactory {
    public static void main(String[] args) {
        UserService userService = Service.getUserService();
        final String BLABLA_FORMAT = "%s\t%s%n";
        userService.messages("test")
                .forEach(message -> System.out.printf(BLABLA_FORMAT, message.getLocalDateTime(), message.getBlabla()));
    }
}
