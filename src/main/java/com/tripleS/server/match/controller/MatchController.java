package com.tripleS.server.match.controller;

import com.tripleS.server.friend.dto.response.FriendResponseList;
import com.tripleS.server.global.dto.ResponseTemplate;
import com.tripleS.server.match.domain.Match;
import com.tripleS.server.match.dto.request.MatchRequest;
import com.tripleS.server.match.dto.response.MatchInfoResponse;
import com.tripleS.server.match.dto.response.MatchResponse;
import com.tripleS.server.match.dto.response.MatchResponseList;
import com.tripleS.server.match.dto.response.MatchResultResponse;
import com.tripleS.server.match.service.FriendMatchService;
import com.tripleS.server.match.service.MatchResultService;
import com.tripleS.server.match.service.MatchService;
import com.tripleS.server.match.service.RandomMatchService;
import com.tripleS.server.quiz.domain.Quiz;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatchController { //userId로 넘기는건 다 토큰으로 바꾸기

    private final RandomMatchService randomMatchService;
    private final FriendMatchService friendMatchService;
    private final MatchService matchService;
    private final MatchResultService matchResultService;

    @PostMapping("/matches")
    @ResponseBody
    public Long findMatch(@RequestBody MatchRequest matchRequest) {
        return randomMatchService.findMatch(matchRequest.getUserId(), matchRequest.getCreateTime());
    }

    @PostMapping("/matches/{matchId}/status")
    public void MatchStatus(@PathVariable Long matchId) {
        randomMatchService.matchStatus(matchId);
    }

    @PostMapping("/matches/{friendId}")
    public Long friendMatch(@PathVariable Long friendId, @RequestBody MatchRequest matchRequest) {
        return friendMatchService.friendMatch(friendId, matchRequest);
    }

    @GetMapping("/matches/friend")
    public ResponseTemplate<?> matchList(Long userId) {
        List<MatchResponse> friendMatches = friendMatchService.matchList(userId);
        return ResponseTemplate.from(MatchResponseList.from(friendMatches));
    }

    @PostMapping("matches/{matchId}/accept")
    public ResponseTemplate<?> acceptMatch(@PathVariable Long matchId) {
        friendMatchService.acceptMatch(matchId);
        return ResponseTemplate.EMPTY_RESPONSE;
    }

    @PostMapping("/matches/{matchId}/reject")
    public ResponseTemplate<?> rejectMatch(@PathVariable Long matchId) {
        friendMatchService.rejectMatch(matchId);
        return ResponseTemplate.EMPTY_RESPONSE;
    }

    /*
    @GetMapping("/matches/{matchId}/start")
    @ResponseBody
    public MatchInfoResponse startMatch(@PathVariable Long matchId, Long userId) {
        return matchService.startMatch(matchId, userId);
    }

    @GetMapping("/matches/{matchId}/quiz")
    @ResponseBody
    public List<Quiz> startQuizForMatch(@PathVariable Long matchId) { //퀴즈 한번에 5개 가져옴(임시)
        return matchService.startQuizForMatch(matchId);
    }

    @GetMapping("/matches/{matchId}/quiz/{quizId}/answer")
    @ResponseBody
    public boolean checkQuizForMatch(@PathVariable Long quizId, String myAnswer) {
        return matchService.checkQuizForMatch(quizId, myAnswer);
    }

    @PostMapping("/matches/{matchId}/quiz/{quizId}/result") //퀴즈 답 맞췄을때 처리. 틀렸을땐 프론트에서 3초 멈춤
    @ResponseBody
    public void rightQuizForMatch(@PathVariable Long matchId, Long userId) {
        matchService.rightQuizForMatch(matchId, userId);
    }

    @GetMapping("/matches/{matchId}/result")
    @ResponseBody
    public MatchResultResponse resultQuizForMatch(@PathVariable Long matchId, Long userId) {
        return matchResultService.resultQuizForMatch(matchId, userId);
    }
    */
}
