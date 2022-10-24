package com.example.hello.dao;

import com.example.hello.model.Account;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> getAccountByName(String name);

}
