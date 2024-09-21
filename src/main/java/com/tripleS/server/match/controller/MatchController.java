package com.tripleS.server.match.controller;

import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.match.dto.request.MatchRequest;
import com.tripleS.server.match.dto.response.MatchResponse;
import com.tripleS.server.match.dto.response.MatchResponseList;
import com.tripleS.server.match.dto.response.MatchResultResponse;
import com.tripleS.server.match.dto.response.RankingResponseList;
import com.tripleS.server.match.service.*;
import com.tripleS.server.user.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final RandomMatchService randomMatchService;
    private final FriendMatchService friendMatchService;
    private final MatchService matchService;
    private final MatchResultService matchResultService;
    private final MatchRankingService matchRankingService;

    @PostMapping("/matches")
    @ResponseBody
    public Long findMatch(@AuthenticationPrincipal AuthUser authUser, @RequestBody MatchRequest matchRequest) {
        LocalDateTime creatTime = LocalDateTime.parse(matchRequest.creatTime());
        return randomMatchService.findMatch(authUser.userId(), creatTime);
    }

    @PostMapping("/matches/{friendId}")
    @ResponseBody
    public Long friendMatch(@PathVariable Long friendId, @AuthenticationPrincipal AuthUser authUser, @RequestBody MatchRequest matchRequest) {
        LocalDateTime creatTime = LocalDateTime.parse(matchRequest.creatTime());
        return friendMatchService.friendMatch(friendId, authUser.userId(), creatTime);
    }

    @GetMapping("/matches/friend")
    @ResponseBody
    public ResponseTemplate<?> matchList(@AuthenticationPrincipal AuthUser authUser) {
        List<MatchResponse> friendMatches = friendMatchService.matchList(authUser.userId());
        return ResponseTemplate.from(MatchResponseList.from(friendMatches));
    }

    @PostMapping("matches/{matchId}/accept")
    @ResponseBody
    public ResponseTemplate<?> acceptMatch(@PathVariable Long matchId) {
        friendMatchService.acceptMatch(matchId);
        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @PostMapping("/matches/{matchId}/reject")
    @ResponseBody
    public ResponseTemplate<?> rejectMatch(@PathVariable Long matchId) {
        friendMatchService.rejectMatch(matchId);
        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @DeleteMapping("/matches/{matchId}")
    @ResponseBody
    public ResponseTemplate<?> deleteMatch(@PathVariable Long matchId) {
        matchService.deleteMatch(matchId);
        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @PostMapping("/matches/{matchId}/quiz/{quizId}/answer")
    @ResponseBody
    public ResponseTemplate<?> checkQuizForMatch(@PathVariable Long matchId, @PathVariable Long quizId, @RequestBody String userAnswer, @AuthenticationPrincipal AuthUser authUser) {
        matchService.checkQuizForMatch(matchId, quizId, userAnswer, authUser.userId());
        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @GetMapping("/matches/{matchId}/result")
    @ResponseBody
    public ResponseTemplate<?> resultQuizForMatch(@PathVariable Long matchId, @AuthenticationPrincipal AuthUser authUser) {
        MatchResultResponse matchResultResponse = matchResultService.resultQuizForMatch(matchId, authUser.userId());
        return ResponseTemplate.from(matchResultResponse);
    }

    @GetMapping("/matches/rank/all")
    @ResponseBody
    public ResponseTemplate<?> allRanking() {
        List<UserResponse> allRanking = matchRankingService.allRanking();
        return ResponseTemplate.from(RankingResponseList.from(allRanking));
    }

    @GetMapping("/matches/rank/friend")
    @ResponseBody
    public ResponseTemplate<?> friendRanking(@AuthenticationPrincipal AuthUser authUser) {
        List<UserResponse> friendRanking = matchRankingService.friendRanking(authUser.userId());
        return ResponseTemplate.from(RankingResponseList.from(friendRanking));
    }
}
