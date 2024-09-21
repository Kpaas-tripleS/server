package com.tripleS.server.match.dto.response;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.quiz.dto.QuizDto;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.dto.response.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record MatchStartResponse(
        Long matchId,
        UserResponse player,
        UserResponse opponent,
        List<QuizDto> quizzes
) {
    public static MatchStartResponse from(Match match, User player, User opponent, List<QuizDto> quizzes){
        return MatchStartResponse.builder()
                .matchId(match.getId())
                .player(UserResponse.from(player))
                .opponent(UserResponse.from(opponent))
                .quizzes(quizzes)
                .build();
    }
}
