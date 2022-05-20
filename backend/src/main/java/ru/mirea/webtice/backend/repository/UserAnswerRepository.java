package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.webtice.backend.entity.UserAnswer;

import java.util.List;
import java.util.Optional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer,Long> {
    @Query(
            value = "SELECT * FROM user_answer u WHERE u.user_id = :user_id and u.test_id = :test_id",
            nativeQuery = true)
    List<UserAnswer> searchUserTestAnswers(@Param("user_id")  Long user_id, @Param("test_id") Long test_id);
}
