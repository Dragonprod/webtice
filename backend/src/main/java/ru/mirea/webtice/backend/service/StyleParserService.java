package ru.mirea.webtice.backend.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.entity.Value;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.HashSet;

@Service
public class StyleParserService {

    @PersistenceContext
    private EntityManager entityManager;

    private final String http = "http://htmlbook.ru/css";
    public StyleParserService() {}

    public void parseStyle(String http) throws IOException {
        Document doc = Jsoup.connect(http).get();
        Element blockContent = doc.select("div#block-content").first();
        String descriptionStyle = blockContent.select("p").first().text();
        String nameStyle= doc.select("h1 > span").first().text();
        Element blockSyntax = blockContent.select("p.example").first();
        Element blockValue = blockContent.select("h3:contains(Значения)").first();
        String second_value = "";
        java.util.Set<Value> values = new HashSet<>();

        Style style = new Style();
        Element param = blockContent.select(".param2").first();
        if (param != null) {
            Elements dts = param.select("dt");
            Elements dds = param.select("dd");
            for (int i = 0; i < dds.size(); i++) {
                String dt = dts.get(i).text();
                String dd = dds.get(i).text();
                Value val = new Value();
                val.setValueName(dt);
                val.setDescription(dd);
                values.add(val);
            }
        }
        else{
            if(blockValue == null) {
                blockValue = blockContent.select("p:contains(Значения)").first();
            }
            blockValue = blockValue.nextElementSibling();
            second_value = blockValue.text();
        }


        style.setStyleName(nameStyle);
        style.setSecond_value(second_value);
        style.setValues(values);
        style.setDescription(descriptionStyle);
        String syntex = "";
        if (blockSyntax != null){
            syntex = blockSyntax.text();
        }
        else{
            Element table = blockContent.select("table.syntax").first();
            if (table != null){
                Elements trs = table.select("tr");
                Element lastTr = trs.get(trs.size()-1);
            }
        }
        style.setSyntax(syntex);
        addToDatabase(style);

    }

    public void addToDatabase(Style style){
        Session session = entityManager.unwrap(Session.class);
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(style);
            for (Value val : style.getValues()){
                val.setStyle(style);
                session.persist(val);
            }
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

    public void start() throws IOException {
        Document doc = Jsoup.connect(this.http).get();
        System.out.println(1);
        Element docTags = doc.select("div#block-content").first();
        Elements styles = docTags.select("li > a");
        for(Element style: styles){
            System.out.println(style);
            if(style.text().equals("-ms-interpolation-mode")){
                break;
            }
            parseStyle(style.attr("abs:href"));
        }
    }
}
