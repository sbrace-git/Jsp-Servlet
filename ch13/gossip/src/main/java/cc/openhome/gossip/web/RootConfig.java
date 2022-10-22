package cc.openhome.gossip.web;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.support.TransactionTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
@ComponentScan("cc.openhome.gossip")
public class RootConfig {

    @Bean
    public DataSource getDataSource() {
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:/comp/env");
            return (DataSource) context.lookup("jdbc/gossip/h2");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TransactionTemplate getTransactionTemplate(DataSourceTransactionManager dataSourceTransactionManager) {
        return new TransactionTemplate(dataSourceTransactionManager);
    }

    @Bean
    public JavaMailSender getMailSession(@Value("${mail.username}") String username,
                                         @Value("${mail.password}") String password,
                                         @Value("${mail.smtp.host}") String host,
                                         @Value("${mail.smtp.auth}") String auth,
                                         @Value("${mail.smtp.starttls.enable}") String enable,
                                         @Value("${mail.smtp.socketFactory.class}") String socketFactory,
                                         @Value("${mail.smtp.port}") int port) {
        final Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", enable);
        properties.put("mail.smtp.socketFactory.class", socketFactory);
        properties.put("mail.smtp.port", port);
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        return javaMailSender;
    }
}
