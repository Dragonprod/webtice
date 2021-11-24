package ru.mirea.webtice.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id = null;

    @Column(name="question_name")
    private String questionName;

    @OneToMany(mappedBy = "question",
            cascade=CascadeType.ALL)
    @JsonManagedReference
    private List<Answer> answers;

    public Long getId() {
        return id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
