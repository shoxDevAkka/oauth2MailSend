package com.security.oauth.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {

    private static final String EMAIL_CONFIRMATION_SUBJECT = "Confirm your account";

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${send-mail.email.sender.user}")
    private String user;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendConfirmationEmail(String token, String email){
        String message = "Welcome to the site, test token " + token;
        String from = user;
        send(email, from, message);
    }

    @Async
    public void send(String to, String from, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(EMAIL_CONFIRMATION_SUBJECT);
            helper.setText(email);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
