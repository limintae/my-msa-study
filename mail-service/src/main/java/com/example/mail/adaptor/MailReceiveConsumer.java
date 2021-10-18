package com.example.mail.adaptor;

import com.example.mail.config.MailSendDTO;
import com.example.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailReceiveConsumer {

    private final MailService mailService;

    @KafkaListener(topics = "aidream-mail-send-v1", groupId = "aidream-mail-receive-group-01")
    public void sendMail(MailSendDTO mailSendDTO, ConsumerRecord<String, String> record) {
        mailService.send(mailSendDTO);
    }

}
