package com.example.hello.service;

import com.example.hello.model.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountByName(String name);
}
