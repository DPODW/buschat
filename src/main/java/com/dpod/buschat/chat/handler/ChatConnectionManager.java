package com.dpod.buschat.chat.handler;


import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.businfo.exception.BusInfoErrorCode;
import com.dpod.buschat.businfo.exception.BusInfoException;
import com.dpod.buschat.chat.dto.ChatInfoDto;
import com.dpod.buschat.chat.dto.ChatMessageDto;
import com.dpod.buschat.location.dto.LatLonDto;
import com.dpod.buschat.location.service.LocationInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ChatConnectionManager {

    private final ObjectMapper mapper;

    private final LocationInfoService locationInfoService;

    public ChatConnectionManager(ObjectMapper mapper, LocationInfoService locationInfoService) {
        this.mapper = mapper;
        this.locationInfoService = locationInfoService;
    }


    public void addSessionToChatRoom(WebSocketSession webSocketSession, BusStopInfoDto userNearBusStopResult, Map<String, Set<WebSocketSession>> chatRooms) {
        chatRooms.putIfAbsent(userNearBusStopResult.getBusStopId(), ConcurrentHashMap.newKeySet());
        chatRooms.get(userNearBusStopResult.getBusStopId()).add(webSocketSession);
    }


    public int validateUserCount(BusStopInfoDto userNearBusStopResult, Map<String, Set<WebSocketSession>> chatRooms) {
        int userCount = chatRooms.get(userNearBusStopResult.getBusStopId()).size();
        if(userCount >= 50){
            throw new BusInfoException(BusInfoErrorCode.BUSSTOP_CHAT_MAX);
        }
        return userCount;
    }


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



    public void sendChatMessage(TextMessage message, BusStopInfoDto userNearBusStopResult,Map<String, Set<WebSocketSession>> chatRooms) throws IOException {
            ChatMessageDto chatMessage = mapper.readValue(message.getPayload(), ChatMessageDto.class);
            // 메시지 브로드캐스트
            for (WebSocketSession ws : chatRooms.get(userNearBusStopResult.getBusStopId())) {
                if (ws.isOpen()) {
                    ws.sendMessage(new TextMessage(chatMessage.getSender() + ": " + chatMessage.getMessage()));
                }
            }
    }


    public void receivedLocationValidate(TextMessage message,BusStopInfoDto busStopInfoDto)throws IOException {
        /// 프론트에서 n분 마다 위치 정보를 보낼때, 해당 위치가 50m 안인지 검증하는 메소드.
        /// 위치가 특정되어서 오기 때문에 단건 확정. 고로 매개변수 타입을 맞춰주기 위한 list 이다. (실제론 단건)
        /// 50m 바깥일시 get50mRangeBusStop 에서 예외 발생
        LatLonDto latLonDto = mapper.readValue(message.getPayload(), LatLonDto.class);
            List<BusStopInfoDto> busStopInfoDtoList = new ArrayList<>();
            busStopInfoDtoList.add(busStopInfoDto);
            List<Pair<String, Double>> locationPair = locationInfoService.calculateDistance(latLonDto, busStopInfoDtoList);
            locationInfoService.get50mRangeBusStop(locationPair.get(0)); /// List에 값은 1개 고정이라 0번 인덱스 확정
        }

}
