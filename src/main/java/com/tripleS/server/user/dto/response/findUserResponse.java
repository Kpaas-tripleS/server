package com.tripleS.server.user.dto.response;

import com.tripleS.server.user.domain.User;

public record findUserResponse(
        Long userId,
        String nickname
) {
    public static findUserResponse of(User user) {
        return new findUserResponse(
                user.getId(),
                user.getNickname()
        );
    }
}
