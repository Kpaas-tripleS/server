package com.tripleS.server.quiz.dto;

import lombok.Data;

@Data
public class QuizDto {
    private Long quizId;
    private String question;
    private String choiceOne;
    private String choiceTwo;
    private String choiceThree;
    private String choiceFour;
}
