package com.tripleS.server.friend.dto.request;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.friend.domain.Friend;
import com.tripleS.server.user.domain.User;

public record BeFriendRequest(
        Long receiver,
        Long requester
) {
    public Friend toEntity(User requester, User receiver, Boolean isAccepted) {
        return Friend.builder()
                .user(requester)
                .friend(receiver)
                .isAccepted(isAccepted)
                .build();
    }
}
