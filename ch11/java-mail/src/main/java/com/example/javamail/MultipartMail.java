package com.example.javamail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
            String contentType = file.getContentType();
            System.out.println("contentType = " + contentType);
            filePart.setContent(getBytes(file), file.getContentType());
//            mimeMultipart.addBodyPart(filePart);
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

    private byte[] getBytes(Part part) {
        try (InputStream inputStream = part.getInputStream();
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[2048];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
