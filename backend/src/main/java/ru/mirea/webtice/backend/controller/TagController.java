package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.mirea.webtice.backend.dto.response.MessageResponse;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.repository.TagRepository;
import ru.mirea.webtice.backend.service.TagParserService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

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
        return tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Error: Tag is not found."));
    }

    @GetMapping("/{name}")
    public Tag getTagByName(@PathVariable String name) {
        return tagRepository.findByTagName(name).orElseThrow(() -> new RuntimeException("Error: Tag is not found."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") Long id) {
        tagRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Tag with id " + id + " deleted successfully"));
    }

//    @GetMapping("/attributes")
//    public List<Attribute> getAllAttributes(@RequestParam(required = false) Boolean isGlobal,
//                                            @RequestParam(required = false) Boolean isEvent) {
//        List<Attribute> attrsAll = new ArrayList<Attribute>();
//        if (isEvent == null && isGlobal == null){
//            attrsAll = entityService.attributeGetAll();
//        }
//        else {
//            attrsAll = entityService.attributeGetAllWithFilter(isGlobal, isEvent);
//        }
//        return attrsAll;
//    }


}
