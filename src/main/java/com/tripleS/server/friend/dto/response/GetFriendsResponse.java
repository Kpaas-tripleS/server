package com.tripleS.server.friend.dto.response;

import com.tripleS.server.user.domain.User;

public record GetFriendsResponse(
        Long id,
        String name
) {
    public static GetFriendsResponse from(User user) {

        return new GetFriendsResponse(
                user.getId(),
                user.getName()
        );
    }
}
