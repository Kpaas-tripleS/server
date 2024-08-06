package com.tripleS.server.quiz.domain;

import com.tripleS.server.quiz.domain.type.QuizGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "text_url", nullable = false)
    private String textUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "quiz_grade", nullable = false)
    private QuizGrade quizGrade;

    @Column(name = "choice_one", nullable = false)
    private String choiceOne;

    @Column(name = "choice_two", nullable = false)
    private String choiceTwo;

    @Column(name = "choice_three", nullable = false)
    private String choiceThree;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Builder
    public Quiz(String textUrl, QuizGrade quizGrade, String choiceOne, String choiceTwo,
                String choiceThree, String answer) {
        this.textUrl = textUrl;
        this.quizGrade = quizGrade;
        this.choiceOne = choiceOne;
        this.choiceTwo = choiceTwo;
        this.choiceThree = choiceThree;
        this.answer = answer;
    }
}
