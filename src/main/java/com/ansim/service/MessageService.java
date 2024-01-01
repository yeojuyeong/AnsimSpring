//package com.ansim.service;
//
//import com.ansim.dto.MessageDTO;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class MessageService {
//
//    private final Map<String, List<MessageDTO>> messageStorage = new HashMap<>();
//    private final Map<String, String> writerIds = new HashMap<>();
//
//    public void sendMessage(MessageDTO.MessageType type, String boardId, String msg, String senderId) {
//        // 메시지 저장
//        MessageDTO newMessage = new MessageDTO(type, boardId, msg, senderId);
//        messageStorage.computeIfAbsent(boardId, k -> new ArrayList<>()).add(newMessage);
//
//        writerIds.put(boardId, senderId);
//    }
//
//    public List<MessageDTO> getMessagesBoardId(String boardId) {
//        return messageStorage.getOrDefault(boardId, new ArrayList<>());
//    }
//
//    public String getWriterId(String boardId) {
//        return writerIds.get(boardId);
//    }
//}