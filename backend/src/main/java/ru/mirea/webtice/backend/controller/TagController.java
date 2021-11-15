package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.service.EntityServiceImpl;
import ru.mirea.webtice.backend.service.TagParserService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagParserService tagParserService;

    @Autowired
    private EntityServiceImpl entityService;

    TagController(TagParserService parserService){
        this.tagParserService = parserService;
    }

    @PostMapping("/parser")
    public void parseStart() throws IOException {
        tagParserService.start();
    }

    @GetMapping("/{id}")
    public Tag tagGet(@PathVariable Long id) {
        Tag tag = (Tag) entityService.getTag(id);
        return tag;
    }

    @GetMapping("/name")
    public Tag tagGetByName(@RequestParam String name) {
        Tag tag = (Tag) entityService.getTagByName(name);
        System.out.println(tag);
        return tag;
    }

    @GetMapping("")
    public List<Tag> getAllTag() {
        List<Tag> tags = entityService.tagGetAll();
        return tags;
    }

    @GetMapping("/attributes")
    public List<Attribute> getAllAttributes(@RequestParam(required = false) Boolean isGlobal,
                                            @RequestParam(required = false) Boolean isEvent) {
        List<Attribute> attrsAll = new ArrayList<Attribute>();
        if (isEvent == null && isGlobal == null){
            attrsAll = entityService.attributeGetAll();
        }
        else {
            attrsAll = entityService.attributeGetAllWithFilter(isGlobal, isEvent);
        }
        return attrsAll;
    }

    @DeleteMapping("/{id}")
    public HttpStatus tagDelete(@PathVariable Long id) {
        Tag tag = (Tag) entityService.deleteTag(id);
        if(tag == null){
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }

}
