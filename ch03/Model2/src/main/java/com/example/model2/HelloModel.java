package com.example.model2;

import java.util.HashMap;
import java.util.Map;

public class HelloModel {
    private final Map<String, String> messages = new HashMap<>();

    public HelloModel() {
        messages.put("caterpillar", "Hello");
        messages.put("Justin", "Welcome");
        messages.put("momor", "Hi");
    }

    public String doHello(String user) {
        String message = messages.get(user);
        return String.format("%s, %s!", message, user);

    }
}
