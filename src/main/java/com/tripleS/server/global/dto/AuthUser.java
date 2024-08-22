package com.tripleS.server.global.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthUser(
        Long userId,
        List<String> roles
) {
    public static AuthUser of(Long userId, List<String> roles) {
        return AuthUser.builder()
                .userId(userId)
                .roles(roles)
                .build();
    }
}