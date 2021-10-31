package ru.mirea.webtice.backend.controller;

import ru.mirea.webtice.backend.service.ParserService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/parser")
public class ParserController {
    private ParserService parserService;

    ParserController(ParserService parserService){
        this.parserService = parserService;
    }

    @PostMapping("/parse")
    void parseStart() throws IOException {
        parserService.start();
    }
}
