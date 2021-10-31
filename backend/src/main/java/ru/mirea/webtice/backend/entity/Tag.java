package ru.mirea.webtice.backend.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tagName;

    private String description;

    private Boolean isUsed;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE}
    )
    @JoinTable(
            name = "tag_attributes",
            joinColumns = @JoinColumn(name = "tag_id") ,
            inverseJoinColumns =  @JoinColumn(name = "attribute_id")
    )
    private Set<Attribute> attributes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagDescription() {
        return description;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }
}
