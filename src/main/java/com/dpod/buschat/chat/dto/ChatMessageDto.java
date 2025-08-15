package com.dpod.buschat.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class ChatMessageDto {

    private String sender;
    //전송자

    private String message;
    //메세지


    @Builder
    public ChatMessageDto(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
