package com.example.hellomodel2springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @Autowired
    private HelloModel helloModel;

    @RequestMapping("/hello")
    public String hello(String name, Model model) {
        String hello = helloModel.doHello(name);
        model.addAttribute("hello", hello);
        return "hello";
    }
}
