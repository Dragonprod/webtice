package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.webtice.backend.dto.request.AnswerRequest;
import ru.mirea.webtice.backend.dto.request.QuestionRequest;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Answer;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.repository.QuestionRepository;
import ru.mirea.webtice.backend.service.QuestionParserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionParserService questionService;

    @PostMapping("/parse")
    public void parseStart() throws IOException {
        questionService.start();
    }

    @GetMapping("")
    public List<Question> getQuestions(@RequestParam(required = false) Boolean random) {
        if (random != null && random) {
            return questionRepository.getRandomQuestions();
        } else {
            return questionRepository.findAll();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        if (questionRepository.existsByQuestionName(questionRequest.getQuestionName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Name is already in use!"));
        }
        Question question = new Question(questionRequest.getQuestionName());
        Set<AnswerRequest> answerRequests = questionRequest.getAnswers();
        Set<Answer> answers = new HashSet<>();
        answerRequests.forEach(ans -> {
            Answer answer = new Answer(ans.getAnswerName(), ans.getRight());
            answer.setQuestion(question);
            answers.add(answer);
        });
        question.setAnswers(answers);
        questionRepository.save(question);
        return ResponseEntity.ok(new MessageResponse("Question added successfully!"));
    }

    @GetMapping("/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionRepository.findById(id).orElseThrow(() ->new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Question not found"
        ));
    }

    @GetMapping("/name")
    public Question getQuestionByName(@RequestParam String name) {
        return questionRepository.findQuestionByQuestionName(name).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tag not found"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") Long id) {
        questionRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Question with id " + id + " deleted successfully"));
    }
}
