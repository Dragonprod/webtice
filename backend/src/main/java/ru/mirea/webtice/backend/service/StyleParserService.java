package ru.mirea.webtice.backend.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.entity.Value;
import ru.mirea.webtice.backend.repository.StyleRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class StyleParserService {

    @Autowired
    private StyleRepository styleRepository;

    private final String http = "http://htmlbook.ru/css";

    public StyleParserService() {

    }

    public void parseStyle(String http) throws IOException {
        Document doc = Jsoup.connect(http).get();
        Element blockContent = doc.select("div#block-content").first();
        String descriptionStyle = blockContent.select("p").first().text();
        String nameStyle= doc.select("h1 > span").first().text();
        Element blockSyntax = blockContent.select("p.example").first();
        Element blockValue = blockContent.select("h3:contains(Значения)").first();
        String second_value = "";
        Set<Value> values = new HashSet<>();

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
        styleRepository.save(style);
    }

    public void start() throws IOException {
        Document doc = Jsoup.connect(this.http).get();
        Element docTags = doc.select("div#block-content").first();
        Elements styles = docTags.select("li > a");
        for(Element style: styles){
            if(style.text().equals("-ms-interpolation-mode")){
                break;
            }
            parseStyle(style.attr("abs:href"));
        }
    }
}
