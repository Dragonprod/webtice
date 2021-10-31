package ru.mirea.webtice.backend.service;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParserService {
    private final String http = "https://developer.mozilla.org/ru/docs/Web/HTML/Element/a";

    public ParserService() {}

    public void start() throws IOException {
        Document doc = Jsoup.connect(this.http).get();
        Elements h1 = doc.select("h1");
        System.out.println(h1.html());
    }
}
