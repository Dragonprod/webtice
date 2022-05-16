package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.repository.StyleRepository;
import ru.mirea.webtice.backend.service.StyleParserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/style")
public class StyleController {

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private StyleParserService styleParserService;

    @PostMapping("/parse")
    public void parseStart() throws IOException {
        styleParserService.start();
    }

    @GetMapping("")
    public List<Style> getStyles() {
        return styleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Style getStyleById(@PathVariable Long id) {
        return styleRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Style is not found."));
    }

    @GetMapping("/name")
    public Style getStyleByName(@RequestParam String name) {
        return styleRepository.findByStyleName(name).orElseThrow(() -> new RuntimeException("Error: Style is not found."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStyle(@PathVariable("id") Long id) {
        styleRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Style with id " + id + " deleted successfully"));
    }

}