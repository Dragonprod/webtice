package ru.mirea.webtice.backend.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.repository.TagRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class TagParserService {

    @Autowired
    private TagRepository tagRepository;

    private Elements globalAttr;
    private final String http = "http://htmlbook.ru/html";
    private final String httpGlobalAttr = "http://htmlbook.ru/html/attr/common";
    private final String httpAttrEvents = "http://htmlbook.ru/html/attr/event";
    private Set<Attribute> globalAttributes = new HashSet<>();
    private Set<Attribute> eventsAttributes = new HashSet<>();

    public TagParserService() {

    }

    public void parseTag(String http) throws IOException {
        Document doc = Jsoup.connect(http).get();
        Element blockContent = doc.select("div#block-content").first();
        String descriptionTag = blockContent.select("p").first().text();
        String nameTagParse = doc.select("h1 > span").first().text();
        String nameTag = nameTagParse.substring(nameTagParse.indexOf("<")).trim();
        Element blockAttrs = blockContent.select(".param").first();
        Set<Attribute> attributes = new HashSet<>();
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
        tag.setCloseTag(closeTag!=null ? closeTag.nextElementSibling().text() : "Нет");

        tagRepository.save(tag);
    }

    public void parseGlobalAttr() throws IOException{
        Document doc = Jsoup.connect(this.httpGlobalAttr).get();
        Element blockContent = doc.select("div#block-content").first();
        Elements namesAttr = blockContent.select("a");
        Elements descriptionsAttr = blockContent.select("p");
        for (int i = 0; i < namesAttr.size(); i++){
            Attribute attr = new Attribute();
            attr.setAttributeName(namesAttr.get(i).text());
            attr.setDescription(descriptionsAttr.get(i+1).text());
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
            System.out.println(tag);
            parseTag(tag.attr("abs:href"));
        }
    }
}
