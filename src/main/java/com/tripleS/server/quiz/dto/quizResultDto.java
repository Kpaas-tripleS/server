package com.tripleS.server.quiz.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class quizResultDto {
    private Long quizId;
    private Long userId;
    private String userAnswer;
    private Boolean isCorrect;
    private LocalDateTime answeredAt;

}
