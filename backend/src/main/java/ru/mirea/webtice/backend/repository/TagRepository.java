package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query(value = "SELECT tag FROM Tag tag where tag.tagName = ?1")
    public Tag findByFilterName(String tagName);
}
