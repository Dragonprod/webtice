package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.service.EntityServiceImpl;
import ru.mirea.webtice.backend.service.ParserService;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/tag")
public class ParserController {
    private ParserService parserService;

    @Autowired
    private EntityServiceImpl entityService;

    ParserController(ParserService parserService){
        this.parserService = parserService;
    }

    @PostMapping("/parser")
    public void parseStart() throws IOException {
        parserService.start();

    }

    @GetMapping("/{id}")
    public Tag tagGet(@PathVariable Long id) {
        Tag tag = (Tag) entityService.getTag(id);
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

}
