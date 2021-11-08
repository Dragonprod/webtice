package ru.mirea.webtice.backend.service;

import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;

import java.util.List;

public interface EntityService {
    public Tag saveTag(Tag tag);
    public Tag getTag(Long id);
    public List<Tag> tagGetAll();
    public List<Attribute> attributeGetAllWithFilter(Boolean isGlobal, Boolean isEvent);
    public List<Attribute> attributeGetAll();
//    public List<Tag> getTagByName(String tag_name);
}
