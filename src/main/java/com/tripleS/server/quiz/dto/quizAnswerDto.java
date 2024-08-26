package com.tripleS.server.quiz.dto;

import lombok.Data;

@Data
public class quizAnswerDto {
    private Long userId;
    private String answer;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


}
