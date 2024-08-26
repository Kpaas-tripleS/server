package com.tripleS.server.match.service;

import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.domain.type.MatchType;
import com.tripleS.server.match.dto.request.MatchRequest;
import com.tripleS.server.match.dto.response.MatchResponse;
import com.tripleS.server.match.exception.MatchNotFoundException;
import com.tripleS.server.match.exception.UserNotExistException;
import com.tripleS.server.match.exception.errorcode.MatchErrorCode;
import com.tripleS.server.match.repository.MatchRepository;
import com.tripleS.server.user.domain.User;
import com.tripleS.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendMatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;
    private final RandomMatchService randomMatchService;
    private final SimpMessagingTemplate messagingTemplate;

    public Long friendMatch(Long friendId, MatchRequest matchRequest) {
        User user = userRepository.findById(matchRequest.getUserId())
                .orElseThrow(() -> new UserNotExistException(MatchErrorCode.USER_NOT_EXIST));
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotExistException(MatchErrorCode.USER_NOT_EXIST));
        Match match = Match.builder()
                .isMatch(false)
                .createTime(matchRequest.getCreateTime())
                .leader(user)
                .follower(friend)
                .matchType(MatchType.FRIEND)
                .build();
        matchRepository.save(match);
        randomMatchService.matchStatus(match.getId());
        return match.getId();
    }

    public List<MatchResponse> matchList(Long userId) {
        return matchRepository.findFriendMatch(MatchType.FRIEND, userId).stream()
                .map(MatchResponse::from)
                .toList();
    }

    public void acceptMatch(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new MatchNotFoundException(MatchErrorCode.MATCH_NOT_FOUND));
        match.setIsMatch(true);
        matchRepository.save(match);
        //String destination = "/topic/matches/" + matchId + "/status";
        //messagingTemplate.convertAndSend(destination, "MATCH_COMPLETE");
    }

    public void rejectMatch(Long matchId) {
        String destination = "/topic/matches/" + matchId + "/status";
        messagingTemplate.convertAndSend(destination, "MATCH_REJECT");
        randomMatchService.deleteMatch(matchId);
    }

}
