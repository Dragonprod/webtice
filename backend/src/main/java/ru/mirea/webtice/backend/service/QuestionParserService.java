package ru.mirea.webtice.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.repository.QuestionRepository;

import java.io.IOException;

@Service
public class QuestionParserService {
    @Autowired
    private QuestionRepository questionRepository;

    public QuestionParserService() {
    }

    public void start() throws IOException {

    }
}

