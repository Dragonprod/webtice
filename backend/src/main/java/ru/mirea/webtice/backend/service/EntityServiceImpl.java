package ru.mirea.webtice.backend.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.entity.Tag;
import ru.mirea.webtice.backend.repository.AttributeRepository;
import ru.mirea.webtice.backend.repository.StyleRepository;
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

    @Autowired
    private StyleRepository styleRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag saveTag(Tag tag) {
        Tag newTag = tagRepository.save(tag);
        return newTag;
    }

    @Override
    public Tag getTag(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> tagGetAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags;
    }

    @Override
    public List<Attribute> attributeGetAllWithFilter(Boolean isGlobal, Boolean isEvent) {
        List<Attribute> attrs = attributeRepository.findByIsGlobalorIsevent(isGlobal, isEvent);
        return attrs;
    }

    @Override
    public List<Attribute> attributeGetAll() {
        List<Attribute> attrs = attributeRepository.findAll();
        return attrs;
    }

    @Override
    public Tag getTagByName(String tag_name) {
        return tagRepository.findByFilterName(tag_name);
    }

    @Override
    public Style getStyle(Long id) {
        return entityManager.find(Style.class, id);
    }

    @Override
    public List<Style> getStyleAll() {
        List<Style> styles = styleRepository.findAll();
        return styles;
    }

    @Override
    public Style getStyleByName(String styleName) {
        return styleRepository.findByFilterName(styleName);

    }

    @Override
    public Tag deleteTag(Long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            Session session = entityManager.unwrap(Session.class);
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.remove(tag);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return tag;
    }

    @Override
    public Style deleteStyle(Long id) {
        Style style = entityManager.find(Style.class, id);
        if (style != null) {
            Session session = entityManager.unwrap(Session.class);
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.remove(style);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                    e.printStackTrace();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return style;
    }

}