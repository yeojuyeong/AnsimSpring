package com.ansim.controller;

import com.ansim.dto.MessageDTO;
import com.ansim.service.MessageService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {


    private final MessageService messageService;
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/message")
    public void send(SendMessage message) {
        if (MessageDTO.MessageType.SEND.equals(message.getType())) {
            message.setDetailMessage(message.getSenderId() + "님이 안심동행을 요청하셨습니다");
            messageService.sendMessage(MessageDTO.MessageType.SEND, message.getRoomId(), message.getDetailMessage(), message.getSenderId());
        }
//        else {
//            messageService.sendMessage(MessageDTO.MessageType.TALK, message.getRoomId(), message.getDetailMessage(), message.getSenderId());
//        }
        log.info("boardID = {}", message.getRoomId());

        // 게시판 작성자에게 메시지를 보냅니다.
        Long writerId = messageService.getWriterId(message.getRoomId()); // 작성자 ID를 가져오는 예시 함수
        if (writerId != null) {
            // 작성자에게 메시지를 보냅니다.
            sendingOperations.convertAndSendToUser(writerId.toString(), "/private", message);
        }

        // 채팅방 내 모든 사용자에게 메시지를 보냅니다.
        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
    }

    @Data
    private static class SendMessage {
        MessageDTO.MessageType type;
        Long roomId;
        String detailMessage;
        Long senderId;
    }
}