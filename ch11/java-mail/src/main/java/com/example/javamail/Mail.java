package com.example.javamail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

@WebServlet(urlPatterns = "/mail")
public class Mail extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        InputStream resourceAsStream = getServletContext().getResourceAsStream("/WEB-INF/classes/mail.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);

        String username = properties.getProperty("mail.username");
        String password = properties.getProperty("mail.password");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(username));
            message.setSubject("subject");
            message.setSentDate(new Date());
            message.setText("text");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().println("send success");
    }
}
