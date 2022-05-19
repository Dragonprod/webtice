package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Query(value="SELECT * FROM Question q ORDER BY random() LIMIT 10", nativeQuery = true)
    List<Question> getRandomQuestions();

    @Query(value="SELECT * FROM Question u WHERE u.id IN :ids", nativeQuery = true)
    Set<Question> getQuestionByIds(@Param("ids") List<Long> ids);

    Optional<Question> findQuestionByQuestionName(String questionName);
    Boolean existsByQuestionName(String questionName);
}