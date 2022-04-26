package com.example.jstl;

import java.util.ResourceBundle;

public class Hello {
    public static void main(String[] args) {
        // native2ascii -encoding UTF-8 .\messages.properties .\1.properties
        ResourceBundle messages = ResourceBundle.getBundle("messages");
        String welcome = messages.getString("cc.openhome.welcome");
        String name = messages.getString("cc.openhome.name");
        System.out.println(welcome);
        System.out.println(name);
    }
}
