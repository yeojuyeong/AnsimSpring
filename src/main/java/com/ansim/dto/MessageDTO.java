package com.ansim.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {
    public enum MessageType{
        SEND
    }

    private MessageType type;
    private Long boardId;
    private Long senderId;
    private String message;

    public MessageDTO(MessageType type, Long boardId, String message, Long senderId) {
        this.type = type;
        this.boardId = boardId;
        this.message = message;
        this.senderId = senderId;
    }
}