package com.tripleS.server.user.dto.response;

import com.tripleS.server.user.domain.User;

public record FindUserResponse(
        Long userId,
        String nickname
) {
    public static FindUserResponse of(User user) {
        return new FindUserResponse(
                user.getId(),
                user.getNickname()
        );
    }
}
