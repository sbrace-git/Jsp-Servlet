package com.example.hellojsp.service.impl;

import com.example.hellojsp.dao.AccountDao;
import com.example.hellojsp.model.Account;
import com.example.hellojsp.service.AccountService;
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
