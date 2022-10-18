package cc.openhome.gossip.listener;

import cc.openhome.gossip.dao.AccountDao;
import cc.openhome.gossip.dao.MessageDao;
import cc.openhome.gossip.dao.impl.AccountDaoImpl;
import cc.openhome.gossip.dao.impl.MessageDaoImpl;
import cc.openhome.gossip.service.EmailService;
import cc.openhome.gossip.service.impl.NetEaseMailServiceImpl;
import cc.openhome.gossip.service.impl.UserServiceImpl;
import cc.openhome.gossip.template.JdbcTemplate;
import cc.openhome.gossip.template.impl.JdbcTemplateImpl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class ServiceContainer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        DataSource dataSource;
        try {
            InitialContext initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:/comp/env");
            dataSource = (DataSource) context.lookup("jdbc/gossip/h2");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(dataSource);
        AccountDao accountDao = new AccountDaoImpl(jdbcTemplate);
        MessageDao messageDao = new MessageDaoImpl(jdbcTemplate);

        servletContext.setAttribute("userService", new UserServiceImpl(accountDao, messageDao));

        Properties mailProperties = getMailProperties(servletContext);
        Session mailSession = getMailSession(mailProperties);
        EmailService emailService = new NetEaseMailServiceImpl(mailSession, mailProperties.getProperty("mail.username"));
        servletContext.setAttribute("emailService", emailService);
    }

    private Properties getMailProperties(ServletContext servletContext) {
        InputStream resourceAsStream = servletContext.getResourceAsStream("/WEB-INF/classes/mail.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private Session getMailSession(Properties properties) {
        String username = properties.getProperty("mail.username");
        String password = properties.getProperty("mail.password");

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
