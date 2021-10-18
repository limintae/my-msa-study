package com.example.mail.service;

import com.example.mail.config.MailSendDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(MailSendDTO mailSendDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("limintae88@awasoft.co.kr");
        simpleMailMessage.setTo(mailSendDTO.getSendTo());
        simpleMailMessage.setSubject(mailSendDTO.getSubject());
        simpleMailMessage.setText(mailSendDTO.getText());
        javaMailSender.send(simpleMailMessage);
    }

}
