package com.example.mail.config;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
@Builder
public class MailSendDTO implements Serializable {

    private static final long serialVersionUID = 3159041871381032408L;

    private final String sendTo;
    private final String subject;
    private final String text;

}
