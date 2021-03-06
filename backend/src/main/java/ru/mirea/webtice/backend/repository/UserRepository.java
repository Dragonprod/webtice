package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Test;
import ru.mirea.webtice.backend.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "select t from test t join t.users u where u.id = :user_id", nativeQuery = true)
    List<Test> findAllTestsByUserId(@Param("user_id") Long user_id);
}
