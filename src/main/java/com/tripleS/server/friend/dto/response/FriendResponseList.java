package com.tripleS.server.friend.dto.response;

import java.util.List;

public record FriendResponseList(
        List<FriendResponse> friendResponseList
) {
    public static FriendResponseList from(List<FriendResponse> friendResponseList) {
        return new FriendResponseList(friendResponseList);
    }
}
//friendresponselist