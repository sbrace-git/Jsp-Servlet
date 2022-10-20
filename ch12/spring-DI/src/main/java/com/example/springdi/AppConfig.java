package com.example.springdi;

import com.example.springdi.template.JdbcTemplate;
import com.example.springdi.template.impl.JdbcTemplateImpl;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public DataSource getDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:tcp://localhost:9092/D:/server/H2/data/gossip/gossip");
        jdbcDataSource.setUser("root");
        jdbcDataSource.setPassword("1111");
        return jdbcDataSource;
    }
}




