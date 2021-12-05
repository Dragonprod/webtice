package ru.mirea.webtice.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Answer;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.repository.AnswerRepository;
import ru.mirea.webtice.backend.repository.QuestionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class QuestionServiceImpl  implements QuestionService{

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public Question saveQuestion(Question question) {
        questionRepository.save(question);
        for (Answer answer : question.getAnswers()){
            answer.setQuestion(question);
            answerRepository.save(answer);
        }
        return question;
    }
}
