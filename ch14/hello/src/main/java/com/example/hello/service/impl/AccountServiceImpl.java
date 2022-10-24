package com.example.hello.service.impl;

import com.example.hello.dao.AccountDao;
import com.example.hello.model.Account;
import com.example.hello.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public Optional<Account> getAccountByName(String name) {
        return accountDao.getAccountByName(name);
    }
}
