package com.example.mail.service;

import com.example.mail.config.MailSendDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
class MailServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MailService mailService;

    @Test
    void sendMail() throws Exception {
        MailSendDTO mailSendDTO = MailSendDTO.builder()
                .sendTo("limintae88@daum.net")
                .subject("test subject")
                .text("this is test mail")
                .build();
        mailService.send(mailSendDTO);
    }

}