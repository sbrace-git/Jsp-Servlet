package com.example.hello.dao.impl;

import com.example.hello.dao.AccountDao;
import com.example.hello.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ACCOUNT_BY_NAME_SQL = "select name, email, password, salt from T_ACCOUNT where NAME = ? ";

    private static final ResultSetExtractor<Account> ACCOUNT_RESULT_SET_EXTRACTOR = resultSet -> {
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String salt = resultSet.getString("salt");
            return new Account(name, email, password, salt);
        }
        return null;
    };

    @Override
    public Optional<Account> getAccountByName(String paramName) {
        if (paramName == null || paramName.length() == 0) {
            return Optional.empty();
        }
        Account account = jdbcTemplate.query(SELECT_ACCOUNT_BY_NAME_SQL, ACCOUNT_RESULT_SET_EXTRACTOR, paramName);
        return Optional.ofNullable(account);
    }
}
