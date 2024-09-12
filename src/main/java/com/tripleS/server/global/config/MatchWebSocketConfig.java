package com.tripleS.server.global.config;

import com.tripleS.server.global.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class MatchWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); //발행자가 "/topic"의 경로로 메시지를 주면 구독자들에게 전달
        config.setApplicationDestinationPrefixes("/app"); // 발행자가 "/app"의 경로로 메시지를 주면 가공을 해서 구독자들에게 전달
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
                //.withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        log.info("StompHandler interceptor registered");
        registration.interceptors(stompHandler);
    }

}