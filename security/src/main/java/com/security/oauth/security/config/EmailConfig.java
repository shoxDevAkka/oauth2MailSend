package com.security.oauth.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    private static final int GMAIL_SMTP_PORT = 587;

    @Value("${send-mail.email.sender.host}")
    private String host;

    @Value("${send-mail.email.sender.user}")
    private String user;

    @Value("${send-mail.email.sender.password}")
    private String password;

    @Value("${send-mail.email.sender.debug}")
    private boolean debug;


    @Bean
    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(GMAIL_SMTP_PORT);

        javaMailSender.setUsername(user);
        javaMailSender.setPassword(password);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", debug);
        return javaMailSender;
    }

}
