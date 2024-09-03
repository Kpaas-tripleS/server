package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.MatchResult;
import com.tripleS.server.match.dto.response.MatchStartResponse;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.match.repository.MatchResultRepository;
import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.dto.quizDto;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private final SimpMessagingTemplate messagingTemplate;

    public void startMatch(Long matchId) {
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
        List<quizDto> quizzes = quizService.getRandomQuizzesForMatch(5);
        String destination = "/topic/matches/" + matchId;
        messagingTemplate.convertAndSend(destination, MatchStartResponse.from(match, quizzes));
    }

    public void checkQuizForMatch(Long matchId, Long quizId, String userAnswer, Long userId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NullPointerException("해당 퀴즈를 찾을 수 없습니다."));
        if(quiz.getAnswer().equals(userAnswer)) {
            Match match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
            MatchResult matchResult = matchResultRepository.findByMatchId(matchId);
            if(match.getLeader().getId().equals(userId)) matchResultRepository.updateLeaderScore(matchId);
            else matchResultRepository.updateFollowerScore(matchId);
            matchResultRepository.save(matchResult);
            messagingTemplate.convertAndSend("/topic/matches/" + matchId, "QUIZ_RIGHT");
        }
        else {
            messagingTemplate.convertAndSend("/topic/matches/" + matchId, "QUIZ_WRONG");
        }
    }

}
