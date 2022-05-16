package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Style;
import ru.mirea.webtice.backend.entity.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Optional<Tag> findByTagName(String tagName);
}
