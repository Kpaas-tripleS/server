package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.MatchResult;
import com.tripleS.server.match.dto.response.MatchInfoResponse;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.UserNotExistException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.match.repository.MatchResultRepository;
import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
public class MatchService {
/*
    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final QuizRepository quizRepository;
    private final QuizService quizService;

    @Autowired
    public MatchService(MatchRepository matchRepository, MatchResultRepository matchResultRepository, QuizRepository quizRepository, QuizService quizService) {
        this.matchRepository = matchRepository;
        this.matchResultRepository = matchResultRepository;
        this.quizRepository = quizRepository;
        this.quizService = quizService;
    }

    public MatchInfoResponse startMatch(Long matchId, Long userId) {
        MatchInfoResponse response = new MatchInfoResponse();
        Match match = matchRepository.findById(matchId).
                orElseThrow(() -> new UserNotExistException(MatchErrorCode.MATCH_NOT_FOUND));
        if(match.getLeader().getId().equals(userId)) {
            response.setPlayer(match.getLeader());
            response.setOpponent(match.getFollower());
        } else {
            response.setPlayer(match.getFollower());
            response.setOpponent(match.getLeader());
        }

        return response;
    }

    public List<Quiz> startQuizForMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        MatchResult matchResult = matchResultRepository.findByMatchId(matchId);
        if(matchResult == null) {
            matchResult = MatchResult.builder()
                    .leaderScore(0L)
                    .followerScore(0L)
                    .match(match)
                    .build();
            matchResultRepository.save(matchResult);
        }
        return quizService.getRandomQuizzes();
    }

    public boolean checkQuizForMatch(Long quizId, String myAnswer) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NullPointerException("해당 퀴즈를 찾을 수 없습니다."));
        return quiz.getAnswer().equals(myAnswer);
    }

    public void rightQuizForMatch(Long matchId, Long userId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        MatchResult matchResult = matchResultRepository.findByMatchId(match.getId());
        if(match.getLeader().getId().equals(userId)) matchResultRepository.updateLeaderScore(matchId);
        else matchResultRepository.updateFollowerScore(matchId);
        matchResultRepository.save(matchResult);
    }
*/
}
