package com.dpod.buschat.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatInfoDto {

    private String chatRoomName;
    //채팅방 이름

    private int userCount;
    //채팅방 유저 수

    @Builder
    public ChatInfoDto(String chatRoomName, int userCount) {
        this.chatRoomName = chatRoomName;
        this.userCount = userCount;
    }
}
