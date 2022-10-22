package cc.openhome.gossip.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
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
    public Session getMailSession(@Value("${mail.username}") String username,
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
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        session.setDebug(true);
        return session;
    }
}
