package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.type.MatchType;
import com.tripleS.server.match.exception.UserNotExistException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class RandomMatchService {

    private final MatchService matchService;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;


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
            matchService.matchStatus(userId, match.getId());
        }
        return match.getId();
    }
}

