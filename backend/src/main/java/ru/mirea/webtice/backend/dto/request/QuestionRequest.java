package ru.mirea.webtice.backend.dto.request;

import ru.mirea.webtice.backend.entity.Answer;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class QuestionRequest {
    @NotBlank
    private String questionName;

    @NotBlank
    private Set<AnswerRequest> answers;

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public Set<AnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerRequest> answers) {
        this.answers = answers;
    }
}
