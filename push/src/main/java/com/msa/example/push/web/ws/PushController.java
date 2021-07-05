package com.msa.example.push.web.ws;

import com.msa.example.push.dto.PushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class PushController {

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/push/message")
    public void message(PushDTO pushDTO) {
//        simpMessageSendingOperations.convertAndSend("/sub/push/" + pushDTO.getPushId(), pushDTO);
        simpMessageSendingOperations.convertAndSend("/sub/aaa", pushDTO);
    }

}
