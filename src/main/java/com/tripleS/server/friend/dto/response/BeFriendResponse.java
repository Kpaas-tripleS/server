package com.tripleS.server.friend.dto.response;

import com.tripleS.server.friend.domain.BeFriend;

public record BeFriendResponse(
        Long requesterId,
        String nickname
) {
    public static BeFriendResponse from(BeFriend beFriend) {
        return new BeFriendResponse(beFriend.getRequester().getId(), beFriend.getRequesterNickname());
    }
}
