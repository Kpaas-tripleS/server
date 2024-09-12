package com.tripleS.server.match.dto.response;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.user.dto.response.UserResponse;
import lombok.Builder;

@Builder
public record MatchResponse(
        Long matchId,
        UserResponse leader,
        UserResponse follower
) {
    public static MatchResponse from(Match match){
        return MatchResponse.builder()
                .matchId(match.getId())
                .leader(UserResponse.from(match.getLeader()))
                .follower(UserResponse.from(match.getFollower()))
                .build();
    }
}
