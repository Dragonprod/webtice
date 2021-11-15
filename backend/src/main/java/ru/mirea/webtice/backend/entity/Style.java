package ru.mirea.webtice.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "style")
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="style_name")
    private String styleName;

    @Column(name="description", length = 1024)
    private String description;

    @Column(name="second_value", length = 1024)
    private String second_value;

    @Column(name="syntax", length = 1024)
    private String syntax;

    @OneToMany(mappedBy = "style",
            cascade=CascadeType.ALL)
    @JsonManagedReference
    private Set<Value> values = new HashSet<>();

    public String getStyleName(){
        return this.styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValues(Set<Value> values) {
        this.values = values;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSyntax() {
        return syntax;
    }

    public Set<Value> getValues() {
        return values;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public void setSecond_value(String second_value) {
        this.second_value = second_value;
    }

    public String getSecond_value() {
        return second_value;
    }
}


