package com.ansim.service;

import com.ansim.dto.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {

    private final Map<Long, List<MessageDTO>> messageStorage = new HashMap<>();
    private final Map<Long, Long> writerIds = new HashMap<>();

    public void sendMessage(MessageDTO.MessageType type, Long boardId, String message, Long senderId) {
        // 메시지 저장
        MessageDTO newMessage = new MessageDTO(type, boardId, message, senderId);
        messageStorage.computeIfAbsent(boardId, k -> new ArrayList<>()).add(newMessage);

        writerIds.put(boardId, senderId);
    }

    public List<MessageDTO> getMessagesBoardId(Long roomId) {
        return messageStorage.getOrDefault(roomId, new ArrayList<>());
    }

    public Long getWriterId(Long roomId) {
        return writerIds.get(roomId);
    }
}