package ru.mirea.webtice.backend.controller;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.repository.AttributeRepository;
import ru.mirea.webtice.backend.repository.TagRepository;
import ru.mirea.webtice.backend.service.TagParserService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private TagParserService tagParserService;

    @PostMapping("/parse")
    public void parseStart() throws IOException {
        tagParserService.start();
    }

    @GetMapping("")
    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable Long id) {
        return tagRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tag not found"
        ));
    }

    @GetMapping("/name")
    public Tag getTagByName(@RequestParam String name) {
        return tagRepository.findByTagName(name).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Tag not found"
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") Long id) {
        tagRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Tag with id " + id + " deleted successfully"));
    }

    @GetMapping("/attributes")
    public List<Attribute> getAllAttributes(@RequestParam(required = false) Boolean isGlobal, @RequestParam(required = false) Boolean isEvent) {
        List<Attribute> attributes;
        if (isEvent == null && isGlobal == null){
            attributes = attributeRepository.findAll();
        }
        else {
            attributes = attributeRepository.findByIsGlobalorIsevent(isGlobal, isEvent);
        }
        return attributes;
    }
}
