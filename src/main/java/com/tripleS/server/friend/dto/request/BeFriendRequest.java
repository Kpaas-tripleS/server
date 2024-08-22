package com.tripleS.server.friend.dto.request;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.user.domain.User;

public record BeFriendRequest(
        Long receiver,
        Long requester,
        boolean isAccepted,
        String nickname
) {
    public BeFriend toEntity(User requester, User receiver, boolean isAccepted, String nickname) {
        return BeFriend.builder()
                .requester(requester)
                .receiver(receiver)
                .isAccepted(isAccepted)
                .nickname(nickname)
                .build();
    }
}
