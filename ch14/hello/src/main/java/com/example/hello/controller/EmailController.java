package com.example.hello.controller;

import com.example.hello.model.Account;
import com.example.hello.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class EmailController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/addr")
    public String addr(String name, Model model) {
        model.addAttribute("addr", String.format("%s@openhome.cc", name));
        return "addr";
    }

    @RequestMapping("/addrByName")
    public String addrByName(String name, Model model) {
        Optional<Account> accountByName = accountService.getAccountByName(name);
        accountByName.ifPresent(account -> model.addAttribute("addr", account.getEmail()));
        return "addr";
    }
}
