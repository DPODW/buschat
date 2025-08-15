package com.dpod.buschat.chat.handler;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.chat.dto.ChatInfoDto;
import com.dpod.buschat.chat.dto.ChatMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>(); /// Set 형식 : 중복을 허용하지 않음

    private final ChatConnectionManager chatConnectionManager;

    @Autowired
    public ChatHandler(ChatConnectionManager chatConnectionManager) {
        this.chatConnectionManager = chatConnectionManager;
    }

    // 연결 시 세션을 방에 등록
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
        BusStopInfoDto userNearBusStopResult = (BusStopInfoDto) webSocketSession.getAttributes().get("userNearBusStopResult");
        chatConnectionManager.addSessionToChatRoom(webSocketSession,userNearBusStopResult,chatRooms);
        int userCount = chatConnectionManager.validateUserCount(userNearBusStopResult, chatRooms);
        chatConnectionManager.updateChatRoomsInfo(userNearBusStopResult,chatRooms,userCount);
    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {
        BusStopInfoDto userNearBusStopResult = (BusStopInfoDto) webSocketSession.getAttributes().get("userNearBusStopResult");
        if(message.getPayload().contains("validate-001")){
            /// validate-001 : validate-001 이 포함된 json 데이터는 위치 검증 데이터로 판별
            chatConnectionManager.receivedLocationValidate(message,userNearBusStopResult);
        }else{
            chatConnectionManager.sendChatMessage(message,userNearBusStopResult,chatRooms);
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) throws IOException {
        BusStopInfoDto userNearBusStopResult = (BusStopInfoDto) webSocketSession.getAttributes().get("userNearBusStopResult");
        chatRooms.get(userNearBusStopResult.getBusStopId()).remove(webSocketSession);
        int userCount = chatConnectionManager.validateUserCount(userNearBusStopResult, chatRooms);
        chatConnectionManager.updateChatRoomsInfo(userNearBusStopResult,chatRooms,userCount);
    }
}
