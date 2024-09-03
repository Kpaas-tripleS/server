package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.MatchResult;
import com.tripleS.server.match.dto.response.MatchResultResponse;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.match.repository.MatchResultRepository;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class MatchResultService {

    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final UserRepository userRepository;

    public MatchResultResponse resultQuizForMatch(Long matchId, Long userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        MatchResult matchResult = matchResultRepository.findByMatchId(matchId);
        if(match.getLeader().getId().equals(userId)) {
            userRepository.updateWinCount(userId);
            return MatchResultResponse.from(match, match.getLeader(), match.getFollower(), matchResult.getLeaderScore(), matchResult.getFollowerScore());
        } else {
            return MatchResultResponse.from(match, match.getFollower(), match.getLeader(), matchResult.getFollowerScore(), matchResult.getLeaderScore());
        }
    }

}
