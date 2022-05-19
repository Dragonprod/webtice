package ru.mirea.webtice.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Question;
import ru.mirea.webtice.backend.entity.Test;
import ru.mirea.webtice.backend.entity.UserAnswer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test,Long> {
}
