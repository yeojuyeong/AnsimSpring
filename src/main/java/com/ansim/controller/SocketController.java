//package com.ansim.controller;
//
//import com.ansim.model.Message;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class SocketController {
//
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
////
////    private SimpMessageSendingOperations messagingTemplate;
////
////    // stomp 테스트 화면
////    @GetMapping("/alarm/stomp")
////    public String stompAlarm() {
////        return "/stomp";
////    }
////
////    @MessageMapping("/{userId}")
////    public void message(@DestinationVariable("userId") Long userId) {
////        messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
////    }
////
//    @MessageMapping("/message")
//    @SendTo("/apply/public")
//    public Message receiveMessage(@Payload Message message){
//        return message;
//    }
//    @MessageMapping("/private-message")
//    public Message recMessage(@Payload Message message){
//        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
//        System.out.println(message.toString());
//        return message;
//    }
//}