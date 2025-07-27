package com.dpod.buschat.chat.config;

import com.dpod.buschat.chat.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    private final ChatHandShakeInterceptor chatHandShakeInterceptor;

    public WebSocketConfig(ChatHandler chatHandler, ChatHandShakeInterceptor chatHandShakeInterceptor) {
        this.chatHandler = chatHandler;
        this.chatHandShakeInterceptor = chatHandShakeInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/bus/chat")
                .setAllowedOrigins("*")
                .addInterceptors(chatHandShakeInterceptor);
    }
}
