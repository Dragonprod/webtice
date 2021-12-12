package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mirea.webtice.backend.entity.Answer;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.service.EntityServiceImpl;
import ru.mirea.webtice.backend.service.QuestionServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private EntityServiceImpl entityService;

    @Autowired
    private QuestionServiceImpl questionService;

    @PostMapping("/parser")
    public void parseStart() throws IOException {
        // questionService.start();
    }

    @PostMapping("/add")
    public Question setQuestion(@RequestBody Question question) {
         return questionService.saveQuestion(question);
    }

    @GetMapping("/{id}")
    public Question questionGet(@PathVariable Long id) {
        Question question = (Question) entityService.getQuestion(id);
        return question;
    }

    @GetMapping("")
    public List<Question> questionsGet(){
        return entityService.getRandomQuestions();
    }
}
