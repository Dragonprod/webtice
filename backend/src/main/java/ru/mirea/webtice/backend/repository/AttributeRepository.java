

package ru.mirea.webtice.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.mirea.webtice.backend.entity.Attribute;
import ru.mirea.webtice.backend.entity.Tag;

import java.util.List;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute,Long> {

    @Query(value = "SELECT attr FROM Attribute attr where attr.isGlobal = ?1 and attr.isEvent = ?2")
    public List<Attribute> findByIsGlobalorIsevent(Boolean isGlobal, Boolean isEvent);
}
