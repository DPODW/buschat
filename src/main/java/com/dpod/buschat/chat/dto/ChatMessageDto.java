package com.dpod.buschat.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatMessageDto {

    private String roomId;
    private String sender;
    private String message;

    @Builder
    public ChatMessageDto(String roomId, String sender, String message) {
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
    }
}
