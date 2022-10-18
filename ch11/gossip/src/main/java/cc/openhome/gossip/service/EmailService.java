package cc.openhome.gossip.service;

import cc.openhome.gossip.model.Account;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

// TODO: 2022/10/18 interface
public class EmailService {
    private final Session emailSession;
    private final String emailUsername;

    public EmailService(Session emailSession, String emailUsername) {
        this.emailSession = emailSession;
        this.emailUsername = emailUsername;
    }

    private static final String VALIDATION_LINK_FORMAT = "http://localhost:8080/gossip/verify?email=%s&token=%s";
    private static final String ANCHOR_FORMAT = "<a href='%s'>验证邮箱</a>";
    private static final String HTML_FORMAT = "请点击 %s 启用账号或复制链接至浏览器地址栏:<br><br> %s";
    private static final String SUBJECT = "Gossip 注册结果";
    private static final String FAILED_REGISTRATION_FORMAT = "账号申请失败，用户名称 %s 或邮件 %s 已存在！";

    public void validationLink(Account account) {
        String link = String.format(VALIDATION_LINK_FORMAT, account.getEmail(), account.getPassword());
        String anchor = String.format(ANCHOR_FORMAT, link);
        String html = String.format(HTML_FORMAT, anchor, link);
        try {
            Message message = createMessage(account.getEmail(), html);
            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void failedRegistration(Account account) {
        String html = String.format(FAILED_REGISTRATION_FORMAT, account.getName(), account.getEmail());
        try {
            Message message = createMessage(account.getEmail(), html);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private Message createMessage(String to, String text) throws MessagingException {
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(text, "text/html; charset=UTF-8");
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(mimeBodyPart);
        MimeMessage mimeMessage = new MimeMessage(emailSession);
        mimeMessage.setFrom(new InternetAddress(emailUsername));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        mimeMessage.setSubject(EmailService.SUBJECT);
        mimeMessage.setContent(mimeMultipart);
        return mimeMessage;
    }
}
