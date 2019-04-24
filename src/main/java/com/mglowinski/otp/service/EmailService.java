package com.mglowinski.otp.service;

import com.mglowinski.otp.controller.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    void sendSimpleMessage(EmailDto emailDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(emailDto.getTo());
        mailMessage.setSubject(emailDto.getSubject());
        mailMessage.setText(emailDto.getMessage());

        javaMailSender.send(mailMessage);
    }

}
