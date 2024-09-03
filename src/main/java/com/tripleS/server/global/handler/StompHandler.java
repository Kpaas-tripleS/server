package com.tripleS.server.global.handler;

import com.tripleS.server.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("preSend method called");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = jwtUtil.extractJwt(authHeader);
                jwtUtil.validateToken(token);
                try {
                    jwtUtil.parseClaims(token);
                } catch (Exception e) {
                    log.error("Invalid JWT token: {}", e.getMessage());
                    throw new IllegalStateException("Invalid JWT token", e);
                }
            } else {
                log.error("Authorization header is missing or invalid");
                throw new IllegalStateException("Authorization header is missing or invalid");
            }
        }

        return message;
    }

}
