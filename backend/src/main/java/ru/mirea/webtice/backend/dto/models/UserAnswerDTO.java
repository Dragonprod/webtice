package ru.mirea.webtice.backend.dto.models;

import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Test;
import ru.mirea.webtice.backend.entity.User;
import ru.mirea.webtice.backend.entity.UserAnswer;

public class UserAnswerDTO {
    private Long id;
    private Long user_id;
    private Long test_id;
    private Long question_id;
    private String answer;

    public Long getUser_id() {
        return user_id;
    }

    public Long getTest_id() {
        return test_id;
    }

    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setTest_id(Long test_id) {
        this.test_id = test_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public UserAnswerDTO(UserAnswer userAnswer, User user, Test test, Question question, String answer) {
        this.id = userAnswer.getId();
        this.answer = answer;
        this.user_id = user.getId();
        this.test_id = test.getId();
        this.question_id = question.getId();
    }
}
