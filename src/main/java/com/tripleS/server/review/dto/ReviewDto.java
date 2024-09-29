package com.tripleS.server.review.dto;

import com.tripleS.server.quiz.dto.QuizResultDto;
import com.tripleS.server.quiz.domain.Quiz;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;                         // 리뷰 ID
    private Long userId;                     // 사용자 ID
    private Long quizId;                     // 퀴즈 ID
    private LocalDateTime reviewedAt;        // 복습 날짜 및 시간
    private long wrongCount;                 // 틀린 횟수
    private boolean isReviewed;              // 복습 여부
    private List<QuizResultDto> quizResults; // 리뷰와 연관된 퀴즈 결과 리스트
    private Quiz.DifficultyLevel difficultyLevel;  // 난이도


    // getter와 setter 추가
    public Quiz.DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(Quiz.DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}