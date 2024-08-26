package com.tripleS.server.friend.dto.response;

import com.tripleS.server.friend.domain.Friend;

public record BeFriendResponse(
        Long requesterId,
        String nickname
) {
    public static BeFriendResponse from(Friend friend) {
        return new BeFriendResponse(
                friend.getFriend().getId(),
                friend.getFriend().getNickname());
    }
}