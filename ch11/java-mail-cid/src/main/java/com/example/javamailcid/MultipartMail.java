package com.example.javamailcid;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@MultipartConfig
@WebServlet("/multipart-mail")
public class MultipartMail extends HttpServlet {

    private Session session;

    private String mailUsername;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        session = (Session) servletContext.getAttribute("mailSession");
        mailUsername = (String) servletContext.getAttribute("mail.username");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html; charset=UTF-8");

        String subject = req.getParameter("subject");
        String text = req.getParameter("text");
        String recipient = req.getParameter("recipient");
        Part file = req.getPart("file");

        MimeMultipart mimeMultipart = new MimeMultipart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        MimeBodyPart filePart = new MimeBodyPart();
        try {
            htmlPart.setContent(text, "text/html; charset=UTF-8");
            mimeMultipart.addBodyPart(htmlPart);
            String submittedFileName = file.getSubmittedFileName();
            filePart.setFileName(MimeUtility.encodeText(submittedFileName, StandardCharsets.UTF_8.name(), "B"));
            filePart.setDataHandler(new DataHandler(new ByteArrayDataSource(file.getInputStream(), file.getContentType())));
            mimeMultipart.addBodyPart(filePart);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailUsername));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContent(mimeMultipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        resp.getWriter().println("send success");
    }
}
