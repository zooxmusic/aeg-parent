package com.aeg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Configuration
public class MailConfig {

    /*@Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.from}")
    private String from;

    @Value("${spring.mail.subject}")
    private String subject;*/

    @Bean
    public JavaMailSender javaMailService() {
        return new JavaMailSenderImpl();
        //JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //javaMailSender.setHost(host);
        //return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();

        //SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //simpleMailMessage.setFrom(from);
        //simpleMailMessage.setSubject(subject);
        //return simpleMailMessage;
    }




}
