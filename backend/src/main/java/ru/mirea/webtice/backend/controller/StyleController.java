package ru.mirea.webtice.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.service.EntityServiceImpl;
import ru.mirea.webtice.backend.service.StyleParserService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/style")
public class StyleController {

    @Autowired
    private EntityServiceImpl entityService;

    @Autowired
    private StyleParserService styleParserService;

    @PostMapping("/parser")
    public void parseStart() throws IOException {
        styleParserService.start();
    }

    @GetMapping("/{id}")
    public Style tagGet(@PathVariable Long id) {
        Style style = (Style) entityService.getStyle(id);
        return style;
    }

    @GetMapping("")
    public List<Style> getAllStyle() {
        List<Style> styles = entityService.getStyleAll();
        return styles;
    }

    @GetMapping("/name")
    public Style tagGetByName(@RequestParam String styleName) {
        Style style = (Style) entityService.getStyleByName(styleName);
        return style;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteStyle(@PathVariable Long id) {
        Style style = (Style) entityService.deleteStyle(id);
        if (style == null) {
            return HttpStatus.NOT_FOUND;
        }
        return HttpStatus.OK;
    }

}