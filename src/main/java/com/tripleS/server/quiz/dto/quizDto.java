package com.tripleS.server.quiz.dto;

import lombok.Data;

@Data
public class quizDto {
    private Long id;
    private String question;
    private String choiceOne;
    private String choiceTwo;
    private String choiceThree;
    private String choiceFour;
}
