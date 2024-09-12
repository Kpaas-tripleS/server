package com.tripleS.server.match.dto.response;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.dto.response.UserResponse;
import lombok.Builder;

@Builder
public record MatchResultResponse(
        Long matchId,
        UserResponse player,
        UserResponse opponent,
        Long playerScore,
        Long opponentScore
) {
    public static MatchResultResponse from(Match match, User player, User opponent, Long playerScore, Long opponentScore){
        return MatchResultResponse.builder()
                .matchId(match.getId())
                .player(UserResponse.from(player))
                .opponent(UserResponse.from(opponent))
                .playerScore(playerScore)
                .opponentScore(opponentScore)
                .build();
    }
}
