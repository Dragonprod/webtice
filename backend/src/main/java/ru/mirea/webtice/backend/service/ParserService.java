package ru.mirea.webtice.backend.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.HashSet;

@Service
public class ParserService<Set> {

    @PersistenceContext
    private EntityManager entityManager;

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
        String nameTagParse = doc.select("h1 > span").first().text();
        String nameTag = nameTagParse.substring(nameTagParse.indexOf("<")).trim();
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
        tag.setCloseTag(closeTag!=null ? closeTag.nextElementSibling().text() : "Нет");
        addToDatabase(tag);
    }

    public void addToDatabase(Tag tag){
        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;
        try{
            for(Attribute attr : tag.getAttributes()){
                session.saveOrUpdate(attr);
            }
            transaction = session.beginTransaction();
            session.persist(tag);
            transaction.commit();
        }
        catch(Exception e){
            if(transaction !=null){
                transaction.rollback();
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        finally{
            session.close();
        }

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
