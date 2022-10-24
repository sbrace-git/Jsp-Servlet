package com.example.hellomodel2springboot;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HelloModel {

    private static final Map<String, String> map = new HashMap<>();

    static {
        map.put("caterpillar", "Hello");
        map.put("Justin", "Welcome");
        map.put("momor", "Hi");
    }

    public String doHello(String name) {
        String hello = map.get(name);
        return String.format("%s, %s!", name, hello);
    }
}
