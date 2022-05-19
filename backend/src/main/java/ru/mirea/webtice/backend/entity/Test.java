package ru.mirea.webtice.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE}
    )
    @JoinTable(
            name = "test_questions",
            joinColumns = @JoinColumn(name = "test_id") ,
            inverseJoinColumns =  @JoinColumn(name = "question_id")
    )
    @JsonManagedReference
    private Set<Question> questions = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}, mappedBy = "tests"
    )
    @JsonBackReference
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
