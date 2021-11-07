package ru.mirea.webtice.backend.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String attributeName;

    @Column(name="description", columnDefinition="TEXT")
    private String description;

    @Column(name="is_glbobal", columnDefinition = "boolean default false")
    private Boolean isGlobal = false;

    @Column(name="is_event", columnDefinition = "boolean default false")
    private Boolean isEvent = false;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}, mappedBy = "attributes"
    )
    private Set<Tag> tags = new HashSet<>();

    public long getId() {
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

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public void setIsGlobal() {
        this.isGlobal = true;
    }

    public void setIsEvent() {
        this.isEvent = true;
    }

    public Boolean getIsGlobal() {
        return isGlobal;
    }

    public Boolean getIsEvent() {
        return isEvent;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


