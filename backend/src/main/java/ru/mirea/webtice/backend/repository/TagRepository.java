package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
//    List<Tag> findByName(String tag_name);
}
