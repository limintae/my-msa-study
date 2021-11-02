package com.example.mail.service;

import com.example.mail.config.MailSendDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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

    @Override
    public void sendHtml(MailSendDTO mailSendDTO) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setFrom("limintae88@awasoft.co.kr");
            mimeMessage.setContent(mailSendDTO.getText(), "text/html");
            helper.setTo(mailSendDTO.getSendTo());
            helper.setSubject(mailSendDTO.getSubject());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
