package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Question;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query(value="SELECT '*' FROM users ORDER BY rand() LIMIT 10", nativeQuery = true)
    List<Question> getRandomQuestions();
    Optional<Question> findQuestionByQuestionName(String questionName);
    Boolean existsByQuestionName(String questionName);
}