package com.tripleS.server.quiz.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    @Column(nullable = false)
    private String question;

    @Column(name = "choice_one", nullable = false)
    private String choiceOne;

    @Column(name = "choice_two", nullable = false)
    private String choiceTwo;

    @Column(name = "choice_three", nullable = false)
    private String choiceThree;

    @Column(name = "choice_four", nullable = false)
    private String choiceFour;

    @Column(nullable = false)
    private String answer;

    @Column(name = "is_solved", nullable = false)
    private Boolean isSolved = false;  // 기본값을 여기서만 설정

    @Builder
    public Quiz(String question, String choiceOne, String choiceTwo, String choiceThree, String choiceFour) {
        this.question = question;
        this.choiceOne = choiceOne;
        this.choiceTwo = choiceTwo;
        this.choiceThree = choiceThree;
        this.choiceFour = choiceFour;
        this.answer = answer;
       ;// isSolved는 기본값인 false를 사용하므로 여기서 설정하지 않음
    }
}