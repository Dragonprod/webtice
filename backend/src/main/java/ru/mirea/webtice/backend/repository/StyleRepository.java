package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Style;

import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style,Long> {
    Optional<Style> findByStyleName(String styleName);
}
