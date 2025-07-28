package com.dpod.buschat.chat.handler;


import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.chat.dto.ChatInfoDto;
import com.dpod.buschat.chat.dto.ChatMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatConnectionManager {

    private final ObjectMapper mapper;

    public ChatConnectionManager(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /// 새로운 세션(유저) 가 들어오면 해당하는 채팅방에 세션을 넣어주는 메소드 
    public void addSessionToChatRoom(WebSocketSession webSocketSession, BusStopInfoDto userNearBusStopResult, Map<String, Set<WebSocketSession>> chatRooms) {
        chatRooms.putIfAbsent(userNearBusStopResult.getBusStopId(), ConcurrentHashMap.newKeySet());
        chatRooms.get(userNearBusStopResult.getBusStopId()).add(webSocketSession);
    }

    /// 현재 누적되어있는 세션 개수 카운트 메소드
    public int validateUserCount(BusStopInfoDto userNearBusStopResult, Map<String, Set<WebSocketSession>> chatRooms) {
        int userCount = chatRooms.get(userNearBusStopResult.getBusStopId()).size();
        if(userCount >= 50){
            throw new RuntimeException();
            //todo:커스텀 예외로 개선 필요
        }
        return userCount;
    }

    /// 채팅방 정보를 모든 채팅방에 업데이트 해주는 메소드
    public void updateChatRoomsInfo(BusStopInfoDto userNearBusStopResult,Map<String, Set<WebSocketSession>> chatRooms,int userCount) throws IOException {
        ChatInfoDto chatInfoDto = ChatInfoDto.builder()
                .chatRoomName(userNearBusStopResult.getBusStopName()+"-"+userNearBusStopResult.getBusStopMark())
                .userCount(userCount)
                .build();
        String chatInfoJson = mapper.writeValueAsString(chatInfoDto);
        //JSON 타입으로 변환
        // 채팅방 정보 -> 채팅방에 사람 들어올때마다 최신화 (브로드캐스트) 모든 채팅방에 늘어나거나 줄어든 유저수를 보내줘야한다.
        for (WebSocketSession ws : chatRooms.get(userNearBusStopResult.getBusStopId())) {
            if (ws.isOpen()) {
                ws.sendMessage(new TextMessage(chatInfoJson));
            }
        }
    }


    /// 메세지 전송 메소드
    public void sendChatMessage(TextMessage message, BusStopInfoDto userNearBusStopResult,Map<String, Set<WebSocketSession>> chatRooms) throws IOException {
        ChatMessageDto chatMessage = mapper.readValue(message.getPayload(), ChatMessageDto.class);

        // 메시지 브로드캐스트
        for (WebSocketSession ws : chatRooms.get(userNearBusStopResult.getBusStopId())) {
            if (ws.isOpen()) {
                ws.sendMessage(new TextMessage(chatMessage.getSender() + ": " + chatMessage.getMessage()));
            }
        }
    }
}
