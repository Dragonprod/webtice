package ru.mirea.webtice.backend.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="tag_name")
    private String tagName;

    @Column(name="description")
    private String description;

    @Column(name="close_tag")
    private String closeTag;

    @Column(name="is_used", columnDefinition = "boolean default false")
    private Boolean isUsed = false;

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

    public String getName() {
        return tagName;
    }

    public String getDescription() {
        return description;
    }

    public String getCloseTag() {
        return closeTag;
    }

    public Boolean getIsUsed() {
        return isUsed;
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setName(String tagName) {
       this.tagName = tagName;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setIsUsed() {
        this.isUsed = true;
    }

    public void setCloseTag(String closeTag) {
        this.closeTag = closeTag;
    }
}