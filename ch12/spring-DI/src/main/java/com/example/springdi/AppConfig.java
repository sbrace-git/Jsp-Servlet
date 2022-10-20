package com.example.springdi;

import com.example.springdi.template.JdbcTemplate;
import com.example.springdi.template.impl.JdbcTemplateImpl;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean
    public JdbcTemplate getDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:tcp://localhost:9092/D:/server/H2/data/gossip/gossip");
        jdbcDataSource.setUser("root");
        jdbcDataSource.setPassword("1111");
        return new JdbcTemplateImpl(jdbcDataSource);
    }
}




