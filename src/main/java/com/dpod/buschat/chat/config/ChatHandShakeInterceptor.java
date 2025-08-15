package com.dpod.buschat.chat.config;

import com.dpod.buschat.businfo.dto.BusStopInfoDto;
import com.dpod.buschat.location.service.LocationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
@Component
public class ChatHandShakeInterceptor implements HandshakeInterceptor {

    private final LocationInfoService locationInfoService;

    public ChatHandShakeInterceptor(LocationInfoService locationInfoService) {
        this.locationInfoService = locationInfoService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String userLocation = request.getURI().getQuery();
        double userLocationLat = Double.parseDouble(userLocation.substring(userLocation.indexOf("=")+1, userLocation.indexOf("&")));
        double userLocationLon = Double.parseDouble(userLocation.substring(userLocation.lastIndexOf("=")+1));

        BusStopInfoDto userNearBusStopResult = locationInfoService.getUserNearBusStopResult(userLocationLat, userLocationLon);
        attributes.put("userNearBusStopResult", userNearBusStopResult);
        /// beforeHandshake 에서는 webSocketSession 과 값을 공유할 수 있는 attributes 를 제공한다. attributes 에 값을 넣으면 추후
        /// webSocketSession 에서도 꺼낼 수 있기 때문에 유용하다.
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
