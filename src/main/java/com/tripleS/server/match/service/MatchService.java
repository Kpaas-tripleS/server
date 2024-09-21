package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.MatchResult;
import com.tripleS.server.match.dto.response.MatchStartResponse;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.match.repository.MatchResultRepository;
import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.dto.QuizDto;
import com.tripleS.server.quiz.repository.QuizRepository;
import com.tripleS.server.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<Long, CompletableFuture<Void>> matchStatusFutures = new ConcurrentHashMap<>();

    public void startMatch(Long userId,Long matchId) {
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
        MatchStartResponse response;
        List<QuizDto> quizzes = quizService.getRandomQuizzesForMatch(5);
        if(match.getLeader().getId().equals(userId)) {
            response = MatchStartResponse.from(match, match.getLeader(), match.getFollower(), quizzes);
        } else {
            response = MatchStartResponse.from(match, match.getFollower(), match.getLeader(), quizzes);
        }
        String destination = "/topic/matches/" + matchId;
        messagingTemplate.convertAndSend(destination, response);
    }

    public void checkQuizForMatch(Long matchId, Long quizId, String userAnswer, Long userId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NullPointerException("해당 퀴즈를 찾을 수 없습니다."));
        if(( "\"" + quiz.getAnswer() + "\"" ).equals(userAnswer)) {
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

    public void matchStatus(Long userId, Long matchId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        matchStatusFutures.put(matchId, future);
        ScheduledFuture<?> timeoutFuture = scheduler.schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new TimeoutException("Match timeout"));
                String destination = "/topic/matches/" + matchId;
                messagingTemplate.convertAndSend(destination, "MATCH_FAIL");
                deleteMatch(matchId);
                matchStatusFutures.remove(matchId);
                future.complete(null);
            }
        }, 30, TimeUnit.SECONDS);
        ScheduledFuture<?>[] statusCheckFutureHolder = new ScheduledFuture<?>[1];
        statusCheckFutureHolder[0] = scheduler.scheduleAtFixedRate(() -> {
            try {
                Match match = matchRepository.findById(matchId)
                        .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
                if (match.getIsMatch()) {
                    startMatch(userId, matchId);
                    future.complete(null);
                    if (!timeoutFuture.isDone()) {
                        timeoutFuture.cancel(false);
                    }
                    if (statusCheckFutureHolder[0] != null && !statusCheckFutureHolder[0].isDone()) {
                        statusCheckFutureHolder[0].cancel(false);
                    }
                    matchStatusFutures.remove(matchId);
                }
            } catch (MatchNotFoundException e) {
                if (!future.isDone()) {
                    String destination = "/topic/matches/" + matchId;
                    messagingTemplate.convertAndSend(destination, "MATCH_FAIL");
                    future.complete(null);
                    if (!timeoutFuture.isDone()) {
                        timeoutFuture.cancel(false);
                    }
                    if (statusCheckFutureHolder[0] != null && !statusCheckFutureHolder[0].isDone()) {
                        statusCheckFutureHolder[0].cancel(false);
                    }
                    matchStatusFutures.remove(matchId);
                }
            }
        }, 0, 3, TimeUnit.SECONDS);
    }

    public void deleteMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        MatchResult matchResult = matchResultRepository.findByMatchId(matchId);
        if(matchResult != null) matchResultRepository.delete(matchResult);
        matchRepository.delete(match);
    }

}
