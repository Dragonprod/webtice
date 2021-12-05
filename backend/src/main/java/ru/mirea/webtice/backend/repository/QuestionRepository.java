package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Question;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

}