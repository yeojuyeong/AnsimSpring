package com.ansim.controller;

import com.ansim.dto.MessageDTO;
import com.ansim.dto.MsgResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class SocketController {

    private final SimpMessageSendingOperations messagingTemplate;

//    // /app/message
//    @MessageMapping("/message")
//    public void addUser(@RequestBody MessageDTO dto) {
//        messagingTemplate.convertAndSend("/queue/private", dto);
//    }


// /app/message
    @MessageMapping("/message")
    public void pubSendMessage(Map<String, String> params) {
        String receiverName = params.get("receiverName");
        String message = params.get("message");
        String senderName = params.get("senderName");

        // 전송자와 메시지 내용을 데이터로 보낸다.
        Map<String, String> data = new HashMap<>();
        data.put("message", message);
        data.put("sender", senderName);

        System.out.println("receiverName, message, senderName : " + receiverName + message + senderName);

        //채널에 구독하고 있는 사용자들 중 모두에게가 아닌 특정한 사용자에게 메세지를 보낼 수 있도록 해주는 메소드
        //convertAndSendToUser(String user, String destination, Object payload)

        // convertAndSendToUser로 특정 유저의 큐에 데이터를 넣어준다.
        this.messagingTemplate.convertAndSendToUser(
                receiverName, "/queue/message",  data
        );
    }

    // 구독한 유저
    // /app/join
    @MessageMapping("/join")
    public void handleWebSocketMessage(@Payload MessageDTO dto) {
        // 클라이언트가 보낸 메시지 처리
        String senderName = dto.getSenderName();
        String status = dto.getStatus();
        //System.out.println("Received Message (Join User):"+ senderName + status);

        // 예를 들어, 응답 메시지를 보내려면:
        MsgResponseDTO response = new MsgResponseDTO();
        response.setMessage("서버에서 응답 메시지 생성");

        // 응답 메시지를 클라이언트로 전송
        //messagingTemplate.convertAndSend("/queue/private", response);
    }

    //
//    @GetMapping(“/direct”)
//    public void directSendMessage(@RequestParam String user) {
//        messagingTemplate.convertAndSendToUser(user, “/queue/message”, new DirectMessage(“test”), createHeaders(null));
//    }

//    private MessageHeaders createHeaders(@Nullable String sessionId) {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        if (sessionId != null) headerAccessor.setSessionId(sessionId);
//        headerAccessor.setLeaveMutable(true);
//        //System.out.println(headerAccessor);
//        return headerAccessor.getMessageHeaders();
//    }

}