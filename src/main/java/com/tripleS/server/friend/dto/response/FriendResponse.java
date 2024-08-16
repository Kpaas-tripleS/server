package com.tripleS.server.friend.dto.response;

import com.tripleS.server.friend.domain.Friend;
import lombok.Builder;

@Builder
public record FriendResponse(
        Long id,
        String name
) {
    public static FriendResponse from(Friend friend) {
        return FriendResponse.builder()
                .id(friend.getFriend().getId())
                .name(friend.getFriend().getName())
                .build();
    }
}
// friendresponse
