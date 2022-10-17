package com.example.javamail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@WebListener
public class ServletContextInitializedListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        Properties mailProperties = getMailProperties(servletContext);
        Session mailSession = getMailSession(mailProperties);

        servletContext.setAttribute("mailSession", mailSession);
        servletContext.setAttribute("mail.username", mailProperties.getProperty("mail.username"));
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
