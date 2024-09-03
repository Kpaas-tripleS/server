package com.tripleS.server.match.dto.response;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.quiz.dto.quizDto;
import com.tripleS.server.user.dto.response.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchStartResponse(
        Long matchId,
        UserResponse leader,
        UserResponse follower,
        List<quizDto> quizzes
) {
    public static MatchStartResponse from(Match match, List<quizDto> quizzes){
        return MatchStartResponse.builder()
                .matchId(match.getId())
                .leader(UserResponse.from(match.getLeader()))
                .follower(UserResponse.from(match.getFollower()))
                .quizzes(quizzes)
                .build();
    }
}
