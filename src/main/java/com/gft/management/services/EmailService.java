package com.gft.management.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarEmail(String para, String corpo, String assunto){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(para);
        message.setText(corpo);
        message.setSubject(assunto);
        message.setFrom("raquelmvc.rldj@gmail.com");
        mailSender.send(message);
    }
}
