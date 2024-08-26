package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.MatchResult;
import com.tripleS.server.match.dto.response.MatchResultResponse;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.match.repository.MatchResultRepository;
import com.tripleS.server.user.repository.UserRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class MatchResultService {

    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final UserRepository userRepository;

    public MatchResultService(MatchRepository matchRepository, MatchResultRepository matchResultRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.matchResultRepository = matchResultRepository;
        this.userRepository = userRepository;
    }

    public MatchResultResponse resultQuizForMatch(Long matchId, Long userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        MatchResult matchResult = matchResultRepository.findByMatchId(match.getId());
        MatchResultResponse response = new MatchResultResponse();
        if(match.getLeader().getId().equals(userId)) {
            userRepository.updateWinCount(userId);
            response.setPlayer(match.getLeader());
            response.setOpponent(match.getFollower());
            response.setPlayerScroe(matchResult.getLeaderScore());
            response.setOpponentScore(matchResult.getFollowerScore());
        } else {
            response.setPlayer(match.getFollower());
            response.setOpponent(match.getLeader());
            response.setPlayerScroe(matchResult.getFollowerScore());
            response.setOpponentScore(matchResult.getLeaderScore());
        }
        return response;
    }

}
