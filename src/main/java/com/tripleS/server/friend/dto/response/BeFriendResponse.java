package com.tripleS.server.friend.dto.response;

import com.tripleS.server.friend.domain.BeFriend;
import com.tripleS.server.friend.domain.Friend;

public record BeFriendResponse(
        Long beFriendId,
        Long requesterId,
        String nickname
) {
    public static BeFriendResponse from(Friend friend) {
        return new BeFriendResponse(
                friend.getId(),
                friend.getUser().getId(),
                friend.getFriend().getNickname());
    }
}