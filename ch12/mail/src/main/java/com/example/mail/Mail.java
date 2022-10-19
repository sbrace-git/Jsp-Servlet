package com.example.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(urlPatterns = "/mail")
public class Mail extends HttpServlet {

    private Session session;

    private String mailUsername;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        session = (Session) servletContext.getAttribute("mailSession");
        mailUsername = (String) servletContext.getAttribute("mail.username");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String recipient = req.getParameter("recipient");
        String text = req.getParameter("text");

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailUsername));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("subject");
            message.setSentDate(new Date());
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().println("send success");
    }
}
