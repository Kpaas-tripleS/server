package com.tripleS.server.friend.dto.request;

public record FriendRequestHandleRequest(
        Long requesterId,
        Boolean isAccepted
) {
}
