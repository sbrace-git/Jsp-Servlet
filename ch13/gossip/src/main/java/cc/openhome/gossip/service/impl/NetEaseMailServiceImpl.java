package cc.openhome.gossip.service.impl;

import cc.openhome.gossip.model.Account;
import cc.openhome.gossip.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Component
public class NetEaseMailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.username}")
    private String emailUsername;

    private static final String VALIDATION_LINK_FORMAT = "http://localhost:8080/gossip/verify?email=%s&token=%s";
    private static final String PASSWORD_RESET_LINK_FORMAT = "http://localhost:8080/gossip/reset-password?name=%s&email=%s&token=%s";
    private static final String REGISTRATION_RESULT_ANCHOR_FORMAT = "<a href='%s'>验证邮箱</a>";
    private static final String RESET_PASSWORD_ANCHOR_FORMAT = "<a href='%s'>重置密码</a>";
    private static final String HTML_FORMAT = "请点击 %s 启用账号或复制链接至浏览器地址栏:<br><br> %s";
    private static final String REGISTRATION_RESULT_SUBJECT = "Gossip 注册结果";
    private static final String RESET_PASSWORD_SUBJECT = "Gossip 重置密码";
    private static final String FAILED_REGISTRATION_FORMAT = "账号申请失败，用户名称 %s 或邮件 %s 已存在！";

    @Override
    public void validationLink(Account account) {
        String link = String.format(VALIDATION_LINK_FORMAT, account.getEmail(), account.getPassword());
        String anchor = String.format(REGISTRATION_RESULT_ANCHOR_FORMAT, link);
        String html = String.format(HTML_FORMAT, anchor, link);
        try {
            MimeMessage message = createMessage(account.getEmail(), html, REGISTRATION_RESULT_SUBJECT);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void failedRegistration(String username, String email) {
        String html = String.format(FAILED_REGISTRATION_FORMAT, username, email);
        MimeMessage message = createMessage(email, html, REGISTRATION_RESULT_SUBJECT);
        javaMailSender.send(message);
    }


    @Override
    public void passwordResetLink(Account account) {
        String link = String.format(PASSWORD_RESET_LINK_FORMAT,
                account.getName(), account.getEmail(), account.getPassword());
        String anchor = String.format(RESET_PASSWORD_ANCHOR_FORMAT, link);
        String html = String.format(HTML_FORMAT, anchor, link);
        MimeMessage message = createMessage(account.getEmail(), html, RESET_PASSWORD_SUBJECT);
        javaMailSender.send(message);
    }

    private MimeMessage createMessage(String to, String text, String subject) {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        try {
            mimeBodyPart.setContent(text, "text/html; charset=UTF-8");
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setFrom(new InternetAddress(emailUsername));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            mimeMessage.setSubject(subject);
            mimeMessage.setContent(mimeMultipart);
            return mimeMessage;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
