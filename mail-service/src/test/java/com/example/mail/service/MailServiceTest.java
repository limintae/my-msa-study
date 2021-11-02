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
                .text("hi!!")
                .build();
        mailService.send(mailSendDTO);
    }

    @Test
    void sendMailHtml() throws Exception {
//        "<html> <body><h1>Hello </h1> </body></html>"
        String htmlMsg = "<body style='border:2px solid black'>"
                +"Your onetime password for registration is  "
                + "Please use this OTP to complete your new user registration."+
                "OTP is confidential, do not share this  with anyone.</body>";

        MailSendDTO mailSendDTO = MailSendDTO.builder()
                .sendTo("limintae88@daum.net")
                .subject("test subject")
                .text(htmlMsg)
                .build();
        mailService.sendHtml(mailSendDTO);
    }

}