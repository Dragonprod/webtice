package ru.mirea.webtice.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id = null;

    @Column(name="answer_name")
    private String answerName;

    @Column(name="is_right", columnDefinition = "boolean default false")
    private Boolean is_right = false;

    @ManyToOne
    @JoinColumn(name="question_id")
    @JsonBackReference
    private Question question;

    public String getAnswerName() {
        return answerName;
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setAnswerName(String answerName) {
        this.answerName = answerName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Boolean getIs_right() {
        return is_right;
    }

    public void setIs_right(Boolean is_right) {
        this.is_right = is_right;
    }
}
