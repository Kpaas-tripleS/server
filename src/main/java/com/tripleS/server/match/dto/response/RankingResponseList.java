package com.tripleS.server.match.dto.response;

import com.tripleS.server.user.dto.response.UserResponse;

import java.util.List;

public record RankingResponseList(
        List<UserResponse> rankingResponseList
) {
    public static RankingResponseList from(List<UserResponse> rankingResponseList) {
        return new RankingResponseList(rankingResponseList);
    }
}
