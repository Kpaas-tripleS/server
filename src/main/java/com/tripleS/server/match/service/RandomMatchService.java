package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.MatchResult;
import com.tripleS.server.match.domain.type.MatchType;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.UserNotExistException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class RandomMatchService {

    private final MatchRepository matchRepository;
    private final MatchResultRepository matchResultRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<Long, CompletableFuture<Void>> matchStatusFutures = new ConcurrentHashMap<>();


    private Optional<Match> findRandomAvailableMatch() {
        List<Match> availableMatches = matchRepository.findByIsMatchFalseAndMatchTypeRandom(MatchType.RANDOM);
        if (availableMatches.isEmpty()) {
            return Optional.empty();
        }
        Random random = new Random();
        return Optional.of(availableMatches.get(random.nextInt(availableMatches.size())));
    }

    public Long findMatch(Long userId, LocalDateTime createTime) {
        Optional<Match> availableMatch = findRandomAvailableMatch();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistException(MatchErrorCode.USER_NOT_EXIST));
        Match match;
        if (availableMatch.isPresent()) {
            match = availableMatch.get();
            match.setIsMatch(true);
            match.setFollower(user);
            matchRepository.save(match);
        } else {
            match = Match.builder()
                    .isMatch(false)
                    .createTime(createTime)
                    .leader(user)
                    .follower(null)
                    .matchType(MatchType.RANDOM)
                    .build();
            matchRepository.save(match);
        }
        return match.getId();
    }

    public void matchStatus(Long matchId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        matchStatusFutures.put(matchId, future);
        ScheduledFuture<?> timeoutFuture = scheduler.schedule(() -> {
            if (!future.isDone()) {
                future.completeExceptionally(new TimeoutException("Match timeout"));
                String destination = "/topic/matches/" + matchId + "/status";
                messagingTemplate.convertAndSend(destination, "MATCH_TIMEOUT");
                deleteMatch(matchId);
                matchStatusFutures.remove(matchId);
            }
        }, 30, TimeUnit.SECONDS);
        ScheduledFuture<?>[] statusCheckFutureHolder = new ScheduledFuture<?>[1];
        statusCheckFutureHolder[0] = scheduler.scheduleAtFixedRate(() -> {
            Match match = matchRepository.findById(matchId)
                    .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
            if (match.getIsMatch()) {
                String destination = "/topic/matches/" + matchId + "/status";
                messagingTemplate.convertAndSend(destination, "MATCH_COMPLETE");
                future.complete(null);
                if (!timeoutFuture.isDone()) {
                    timeoutFuture.cancel(false);
                }
                if (statusCheckFutureHolder[0] != null && !statusCheckFutureHolder[0].isDone()) {
                    statusCheckFutureHolder[0].cancel(false);
                }
                matchStatusFutures.remove(matchId);
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void deleteMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        MatchResult matchResult = matchResultRepository.findByMatchId(matchId);
        matchRepository.delete(match);
        if(matchResult != null) matchResultRepository.delete(matchResult);
    }
}

