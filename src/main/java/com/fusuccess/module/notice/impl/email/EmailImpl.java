package com.fusuccess.module.notice.impl.email;

import com.fusuccess.module.notice.config.EmailConfig;
import com.fusuccess.module.notice.config.NoticeConfig;
import com.fusuccess.module.notice.impl.dingtalk.DingTalkImpl;
import com.fusuccess.module.notice.strategy.NoticeStrategy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailImpl implements NoticeStrategy {
    private static final Logger logger = LogManager.getLogger(DingTalkImpl.class);

    @Override
    public boolean push(String message, NoticeConfig config) {

        if (config == null) {
            logger.error("send email failed, config is null");
            return false;
        }
        EmailConfig emailConfig = config.getEmailConfig();
        String templateType = emailConfig.getTemplateType();
        if (templateType != null) {
            if (templateType.equals("text")) {
                sendTextEmail(config, "系统通知", message);
                return true;
            } else if (templateType.equals("html")) {
                return true;
            }
        } else {
            logger.error("send email failed, templateType is null");
            return false;
        }
        logger.error("send email failed, templateType is not support");
        return false;
    }

    public void sendTextEmail(NoticeConfig config, String subject, String content) {
        try {
            EmailConfig emailConfig = config.getEmailConfig();
            Message message = createMessage(emailConfig);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailConfig.getTo()));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (Exception e) {
            logger.error("send email failed", e);
            throw new RuntimeException(e);
        }
    }

    private Session createSession(EmailConfig config) {
        Properties props = new Properties();
        emailServiceProperties(config,props);
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        });
    }

    private Message createMessage(EmailConfig config) throws MessagingException {
        Message message = new MimeMessage(createSession(config));

        try {
            message.setFrom(new InternetAddress(config.getUsername(), config.getFrom()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }


    private void emailServiceProperties(EmailConfig config,Properties props) {
        props.put("mail.smtp.host", config.getHost());
        props.put("mail.smtp.port", config.getPort());
        props.put("mail.smtp.auth", "true");

        if (config.isSsl()) {
            props.put("mail.smtp.ssl.enable", "true");
        } else {
            props.put("mail.smtp.starttls.enable", "true");
        }
        switch (config.getHost()) {
            case "smtp.qq.com":
                break;
            case "smtp.163.com":
                props.put("mail.smtp.socketFactory.fallback", "false");
                break;
        }

    }
}
