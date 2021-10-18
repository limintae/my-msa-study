package com.example.mail.service;

import com.example.mail.config.MailSendDTO;

public interface MailService {
    void send(MailSendDTO mailSendDTO);
}
