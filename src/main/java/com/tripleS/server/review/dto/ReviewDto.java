package com.tripleS.server.review.dto;

import com.tripleS.server.quiz.dto.QuizResultDto;
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
    private Long id;
    private Long userId;
    private LocalDateTime reviewedAt;
    private List<QuizResultDto> quizResults; //리뷰결과 따로 저장하지 않음
}
