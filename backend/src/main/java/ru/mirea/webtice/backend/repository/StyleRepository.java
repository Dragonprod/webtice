package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Style;

@Repository
public interface StyleRepository extends JpaRepository<Style,Long> {
    @Query(value = "SELECT style FROM Style style where style.styleName = ?1")
    public Style findByFilterName(String styleName);
}
