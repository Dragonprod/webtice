package ru.mirea.webtice.backend.service;

import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.entity.Tag;
import java.util.List;

public interface EntityService {
    public Tag saveTag(Tag tag);
    public Tag getTag(Long id);
    public List<Tag> tagGetAll();
    public List<Attribute> attributeGetAllWithFilter(Boolean isGlobal, Boolean isEvent);
    public List<Attribute> attributeGetAll();
    public Tag getTagByName(String tagName);
    public Style getStyle(Long id);
    public List<Style> getStyleAll();
    public Style getStyleByName(String styleName);
    public Tag deleteTag(Long id);
    public Style deleteStyle(Long id);
    public Question getQuestion(Long id);
    public List<Question> getRandomQuestions();
}

