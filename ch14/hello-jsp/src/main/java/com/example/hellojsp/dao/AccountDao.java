package com.example.hellojsp.dao;

import com.example.hellojsp.model.Account;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> getAccountByName(String name);

}
