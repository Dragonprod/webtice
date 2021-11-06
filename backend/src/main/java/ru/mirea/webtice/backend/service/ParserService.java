package ru.mirea.webtice.backend.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ParserService<Set> {
    private Elements globalAttr;
    private final String http = "http://htmlbook.ru/html";
    private final String httpGlobalAttr = "http://htmlbook.ru/html/attr/common";
    private final String httpAttrEvents = "http://htmlbook.ru/html/attr/event";
    private java.util.Set<Attribute> globalAttributes = new HashSet<>();
    private java.util.Set<Attribute> eventsAttributes = new HashSet<>();
    public ParserService() {}

    public void parseTag(String http) throws IOException {
        Document doc = Jsoup.connect(http).get();
        Element blockContent = doc.select("div#block-content").first();
        String descriptionTag = blockContent.select("p").first().text();
        String nameTag = doc.select("h1 > span").first().text();
        Element blockAttrs = blockContent.select(".param").first();
        java.util.Set<Attribute> attributes = new HashSet<>();
        Element closeTag = blockContent.select("h3:contains(Закрывающий тег)").first();
        if (blockAttrs != null) {
            Elements namesAttrs = blockAttrs.select("dt > a");
            Elements descriptionAttrs = blockAttrs.select("dd");
            for(int i = 0; i < namesAttrs.size(); i++){
                Attribute attr = new Attribute();
                attr.setAttributeName(namesAttrs.get(i).text());
                attr.setDescription(descriptionAttrs.get(i).text());
                attributes.add(attr);
            }
        }
        if (blockContent.select("a[href*=/html/attr/common]").first() != null){
            attributes.addAll(globalAttributes);
        }
        if (blockContent.select("a[href*=/html/attr/event]").first() != null){
            attributes.addAll(eventsAttributes);
        }

        Tag tag = new Tag();
        tag.setDescription(descriptionTag);
        tag.setName(nameTag);
        tag.setAttributes(attributes);
        tag.setCloseTag(closeTag.nextElementSibling().text());
        //addToDatabase(tag);
    }

    public void addToDatabase(Tag tag){
        //adding to database
    }

    public void parseGlobalAttr() throws IOException{
        Document doc = Jsoup.connect(this.httpGlobalAttr).get();
        Element blockContent = doc.select("div#block-content").first();
        Elements namesAttr = blockContent.select("a");
        Elements descriptionsAttr = blockContent.select("p");
        for (int i = 0; i < namesAttr.size(); i++){
            Attribute attr = new Attribute();
            attr.setAttributeName(namesAttr.get(i).text());
            attr.setDescription(descriptionsAttr.get(i).text());
            attr.setIsGlobal();
            this.eventsAttributes.add(attr);
        };
    }

    public void parseGlobalEvents() throws  IOException {
        Document doc = Jsoup.connect(this.httpAttrEvents).get();
        Element blockContent = doc.select("div#block-content").first();
        Elements namesAttr = blockContent.select("a");
        Elements descriptionsAttr = blockContent.select("p");
        for (int i = 0; i < namesAttr.size(); i++){
            Attribute attr = new Attribute();
            attr.setAttributeName(namesAttr.get(i).text());
            attr.setDescription(descriptionsAttr.get(i).text());
            attr.setIsGlobal();
            this.globalAttributes.add(attr);
        }
    }

    public void start() throws IOException {
        Document doc = Jsoup.connect(this.http).get();
        parseGlobalAttr();
        parseGlobalEvents();
        Element docTags = doc.select("div#block-content").first();
        Elements tags = docTags.select("li > a");
        for(Element tag: tags){
            parseTag(tag.attr("abs:href"));
            break;
        }
    }
}
