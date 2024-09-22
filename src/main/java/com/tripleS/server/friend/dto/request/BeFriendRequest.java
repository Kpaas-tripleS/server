package com.tripleS.server.friend.dto.request;

import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.user.domain.User;

public record BeFriendRequest(
        Long receiverId
) {
    public Friend toEntity(User requester, User receiver) {
        return Friend.builder()
                .user(receiver)
                .friend(requester)
                .isAccepted(false)
                .build();
    }
}
