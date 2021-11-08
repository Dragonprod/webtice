package ru.mirea.webtice.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attribute")
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="attribute_name")
    private String attributeName;

    @Column(name="attribute_description", columnDefinition="TEXT")
    private String description;

    @Column(name="is_global", columnDefinition = "boolean default false")
    private Boolean isGlobal = false;

    @Column(name="is_event", columnDefinition = "boolean default false")
    private Boolean isEvent = false;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE}, mappedBy = "attributes"
    )
    @JsonBackReference
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


