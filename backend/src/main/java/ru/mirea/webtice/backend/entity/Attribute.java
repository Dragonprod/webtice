package ru.mirea.webtice.backend.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String attributeName;

    private String description;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}, mappedBy = "attributes"
    )
    private Set<Tag> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getDescription() {
        return description;
    }

    public Set<Tag> getTags() {
        return tags;
    }
}


