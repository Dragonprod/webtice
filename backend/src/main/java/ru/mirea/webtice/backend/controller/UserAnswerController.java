package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.webtice.backend.dto.models.StartTest;
import ru.mirea.webtice.backend.dto.models.UpdateAnswer;
import ru.mirea.webtice.backend.dto.models.UserAnswerDTO;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Test;
import ru.mirea.webtice.backend.entity.User;
import ru.mirea.webtice.backend.entity.UserAnswer;
import ru.mirea.webtice.backend.repository.TestRepository;
import ru.mirea.webtice.backend.repository.UserAnswerRepository;
import ru.mirea.webtice.backend.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/user/answer")
public class UserAnswerController {
    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/start")
    public Set<UserAnswerDTO> startTest(@RequestBody StartTest startTest) {
        Optional<Test> test = testRepository.findById(startTest.getTest_id());
        Optional<User> user = userRepository.findById(startTest.getUser_id());
        if (user.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        if (test.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "test not found"
            );
        }
        Test testGl = test.get();
        User userGl = user.get();
        Set<Test> tests = new HashSet<>();
        tests.add(testGl);
        userGl.setTests(tests);
        Set<UserAnswerDTO> userAnswers = new HashSet<>();
        for (Question question: testGl.getQuestions()){
            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setTest(testGl);
            userAnswer.setUser(userGl);
            userAnswer.setQuestion(question);
            userAnswer.setAnswer("");
            userAnswer = userAnswerRepository.save(userAnswer);
            userAnswers.add(new UserAnswerDTO(userAnswer, userGl,testGl,question,""));
        }
        userRepository.save(userGl);
        return userAnswers;
    }

    @PostMapping("")
    public Set<UserAnswerDTO> getTagById(@RequestBody StartTest startTest) {
        List<UserAnswer> userAnswers= userAnswerRepository.searchUserTestAnswers(startTest.getUser_id(), startTest.getTest_id());
        Set<UserAnswerDTO>  userAnswerDTOS = new HashSet<>();
        for (UserAnswer userAnswer: userAnswers){
            userAnswerDTOS.add(new UserAnswerDTO(userAnswer, userAnswer.getUser(),userAnswer.getTest(),userAnswer.getQuestion(),userAnswer.getAnswer()));
        }
        return userAnswerDTOS;
    }

    @PutMapping("")
    public UserAnswerDTO getTagById(@RequestBody UpdateAnswer updateAnswer) {
       UserAnswer userAnswer = userAnswerRepository.getById(updateAnswer.getAnswer_id());
       userAnswer.setAnswer(updateAnswer.getAnswer());
       userAnswer = userAnswerRepository.save(userAnswer);
       return new UserAnswerDTO(userAnswer,  userAnswer.getUser(),userAnswer.getTest(),userAnswer.getQuestion(),userAnswer.getAnswer());
    }
}
