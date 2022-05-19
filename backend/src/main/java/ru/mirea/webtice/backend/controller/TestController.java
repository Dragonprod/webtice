package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.entity.Test;
import ru.mirea.webtice.backend.repository.QuestionRepository;
import ru.mirea.webtice.backend.repository.TestRepository;

import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestRepository testRepository;


    @Autowired
    private QuestionRepository questionRepository;


    @GetMapping("/{id}")
    public Test getTagById(@PathVariable Long id) {
        return testRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Test not found"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteTagById(@PathVariable Long id) {
        testRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Test with id " + id + " deleted successfully"));
    }

    @PostMapping("")
    public Test  createTest(@RequestBody List<Long> question_ids) {
        Test test = new Test();
        Set<Question> questionSet = questionRepository.getQuestionByIds(question_ids);
        test.setQuestions(questionSet);
        return testRepository.save(test);
    }

}
