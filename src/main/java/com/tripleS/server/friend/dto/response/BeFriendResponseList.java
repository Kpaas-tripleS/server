package com.tripleS.server.friend.dto.response;

import java.util.List;

public record BeFriendResponseList(
        List<BeFriendResponse> beFriendResponseList
) {
    public static BeFriendResponseList from(List<BeFriendResponse> beFriendResponseList) {
        return new BeFriendResponseList(beFriendResponseList);
    }
}
