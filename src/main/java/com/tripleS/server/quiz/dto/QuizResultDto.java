package com.tripleS.server.quiz.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuizResultDto {
    private Long quizId;
    private Long userId;
    private String userAnswer;
    private Boolean isCorrect;
    private LocalDateTime answeredAt;
    private Long resultId;

}
