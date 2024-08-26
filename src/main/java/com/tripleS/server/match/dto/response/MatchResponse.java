package com.tripleS.server.match.dto.response;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.user.dto.response.MatchingUserResponse;
import lombok.Builder;

@Builder
public record MatchResponse(
        Long matchId,
        MatchingUserResponse leader,
        MatchingUserResponse follower
) {
    public static MatchResponse from(Match match){
        return MatchResponse.builder()
                .matchId(match.getId())
                .leader(MatchingUserResponse.from(match.getLeader()))
                .follower(MatchingUserResponse.from(match.getFollower()))
                .build();
    }
}
