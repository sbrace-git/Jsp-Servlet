package com.example.jstl;

import java.util.ResourceBundle;

public class Hello {
    public static void main(String[] args) {
        ResourceBundle messages = ResourceBundle.getBundle("messages");
        String welcome = messages.getString("cc.openhome.welcome");
        String name = messages.getString("cc.openhome.name");
        System.out.printf("%s!", welcome);
        System.out.printf("%s!%n", name);
    }
}
