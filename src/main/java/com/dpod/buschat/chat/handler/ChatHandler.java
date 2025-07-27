package com.dpod.buschat.chat.handler;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.chat.dto.ChatMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> chatRooms = new ConcurrentHashMap<>(); /// Set 형식 : 중복을 허용하지 않음

    // 연결 시 세션을 방에 등록
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        BusStopInfoDto userNearBusStopResult = (BusStopInfoDto) webSocketSession.getAttributes().get("userNearBusStopResult");
        chatRooms.putIfAbsent(userNearBusStopResult.getBusStopId(), ConcurrentHashMap.newKeySet());

        if(chatRooms.get(userNearBusStopResult.getBusStopId()).size() >= 50){
            throw new RuntimeException();
        }

        chatRooms.get(userNearBusStopResult.getBusStopId()).add(webSocketSession);

        //새로고침해도 새로운 브라우저가 열린것은 아니기 때문에 세션이 추가 생성되지는 않는다. (ID 는 변함)

    }

    @Override
    protected void handleTextMessage(WebSocketSession webSocketSession, TextMessage message) throws Exception {
        BusStopInfoDto userNearBusStopResult = (BusStopInfoDto) webSocketSession.getAttributes().get("userNearBusStopResult");
        ObjectMapper mapper = new ObjectMapper();
        ChatMessageDto chatMessage = mapper.readValue(message.getPayload(), ChatMessageDto.class);

        // 메시지 브로드캐스트
        for (WebSocketSession ws : chatRooms.get(userNearBusStopResult.getBusStopId())) {
            if (ws.isOpen()) {
                ws.sendMessage(new TextMessage(chatMessage.getSender() + ": " + chatMessage.getMessage()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        // 연결 종료 시 모든 방에서 제거
        chatRooms.values().forEach(sessions -> sessions.remove(webSocketSession));
    }
}