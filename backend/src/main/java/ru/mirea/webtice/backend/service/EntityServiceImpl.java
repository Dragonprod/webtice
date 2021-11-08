package ru.mirea.webtice.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.repository.AttributeRepository;
import ru.mirea.webtice.backend.repository.TagRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AttributeRepository attributeRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag saveTag(Tag tag) {
        Tag newTag = tagRepository.save(tag);
        return newTag;
    }

    @Override
    @Transactional(readOnly = true)
    public Tag getTag(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tag> tagGetAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attribute> attributeGetAllWithFilter(Boolean isGlobal, Boolean isEvent){
        List<Attribute> attrs = attributeRepository.findByIsGlobalorIsevent(isGlobal, isEvent);
        return attrs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attribute> attributeGetAll(){
        List<Attribute> attrs = attributeRepository.findAll();
        return attrs;
    }

//    @Override
//    public List<Tag> getTagByName(String tag_name) {
//        return entityRepository.findByName(tag_name);
//    }
}