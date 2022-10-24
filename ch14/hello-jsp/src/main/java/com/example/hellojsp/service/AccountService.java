package com.example.hellojsp.service;

import com.example.hellojsp.model.Account;

import java.util.Optional;

public interface AccountService {
    Optional<Account> getAccountByName(String name);
}
