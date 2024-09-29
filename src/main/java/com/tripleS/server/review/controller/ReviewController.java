package com.tripleS.server.review.controller;

import com.tripleS.server.global.dto.AuthUser;
import com.tripleS.server.quiz.domain.Quiz;
import com.tripleS.server.quiz.dto.QuizAnswerDto;
import com.tripleS.server.review.dto.ReviewDto;
import com.tripleS.server.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 랜덤한 5개의 퀴즈를 포함한 리뷰 세션을 생성하는 메서드
    @PostMapping("/user/random-session")
    public ResponseEntity<ReviewDto> createRandomReviewSession(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(required = true) Quiz.DifficultyLevel difficulty) {
        ReviewDto reviewDto = reviewService.createReviewSession(authUser.userId(), difficulty);
        return ResponseEntity.ok(reviewDto);
    }

    // 리뷰 세션에서 특정 퀴즈의 답안을 제출하는 메서드
    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<?> submitReviewAnswer(@PathVariable Long quizId, @RequestBody QuizAnswerDto answerDto) {
        reviewService.submitReviewAnswer(quizId, answerDto.getUserId(), answerDto.getAnswer());
        return ResponseEntity.ok().build();
    }

}