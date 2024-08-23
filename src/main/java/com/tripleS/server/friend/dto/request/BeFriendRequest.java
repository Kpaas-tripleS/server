package com.tripleS.server.friend.dto.request;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.user.domain.User;

public record BeFriendRequest(
        Long receiver
) {
    public BeFriend toEntity(User requester, User receiver) {
        return BeFriend.builder()
                .requester(requester)
                .receiver(receiver)
                .build();
    }
}
