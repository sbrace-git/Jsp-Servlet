package com.example.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmailController {

    @RequestMapping("/addr")
    public String addr(String name, Model model) {
        model.addAttribute("addr", String.format("%s@openhome.cc", name));
        return "addr";
    }
}
