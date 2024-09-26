package com.tripleS.server.review.controller;

import com.tripleS.server.global.dto.AuthUser;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewDto>> getReviewQuizzes(@AuthenticationPrincipal AuthUser authUser) {
        List<ReviewDto> reviewQuizzes = reviewService.getReviewResult(authUser.userId());
        if (reviewQuizzes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reviewQuizzes);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<ReviewDto> getReviewQuizByQuizId(@PathVariable Long quizId) {
        ReviewDto reviewDto = reviewService.getReviewQuizByQuizId(quizId);
        return ResponseEntity.ok(reviewDto);
    }

    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<?> submitReviewAnswer(@PathVariable Long quizId, @RequestBody QuizAnswerDto answerDto) {
        reviewService.submitReviewAnswer(quizId, answerDto.getUserId(), answerDto.getAnswer());
        return ResponseEntity.ok().build();
    }
}