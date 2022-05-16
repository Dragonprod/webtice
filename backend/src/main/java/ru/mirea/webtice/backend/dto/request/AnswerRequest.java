package ru.mirea.webtice.backend.dto.request;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {
    @NotBlank
    private String answerName;

    @NotBlank
    private Boolean isRight;

    public String getAnswerName() {
        return answerName;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public Boolean getRight() {
        return isRight;
    }

    public void setRight(Boolean right) {
        isRight = right;
    }
}
