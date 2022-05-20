package ru.mirea.webtice.backend.dto.models;

public class UpdateAnswer {
    private Long answer_id;
    private String answer;

    public String getAnswer() {
        return answer;
    }

    public Long getAnswer_id() {
        return answer_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswer_id(Long answer_id) {
        this.answer_id = answer_id;
    }
}
