package com.dpod.buschat.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatInfoDto {

    private String chatRoomName;
    private int userCount;

    @Builder
    public ChatInfoDto(String chatRoomName, int userCount) {
        this.chatRoomName = chatRoomName;
        this.userCount = userCount;
    }
}
