package com.example.dependencyinjection;

import com.example.dependencyinjection.dao.AccountDao;
import com.example.dependencyinjection.dao.MessageDao;
import com.example.dependencyinjection.dao.impl.AccountDaoImpl;
import com.example.dependencyinjection.dao.impl.MessageDaoImpl;
import com.example.dependencyinjection.service.impl.UserServiceImpl;
import com.example.dependencyinjection.template.JdbcTemplate;
import com.example.dependencyinjection.template.impl.JdbcTemplateImpl;
import org.h2.jdbcx.JdbcDataSource;

public class Main {
    public static void main(String[] args) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:tcp://localhost:9092/D:/server/H2/data/gossip/gossip");
        jdbcDataSource.setUser("root");
        jdbcDataSource.setPassword("1111");

        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(jdbcDataSource);
        AccountDao accountDao = new AccountDaoImpl(jdbcTemplate);
        MessageDao messageDao = new MessageDaoImpl(jdbcTemplate);

        UserServiceImpl userService = new UserServiceImpl(accountDao, messageDao);

        final String BLABLA_FORMAT = "%s\t%s%n";
        userService.messages("test")
                .forEach(message -> System.out.printf(BLABLA_FORMAT, message.getLocalDateTime(), message.getBlabla()));
    }
}
